package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxLimitSwitch;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.RoborioUtils;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Extender extends GBSubsystem {

	
	
	private static ExtenderState state = ExtenderState.IN_ROBOT_BELLY_LENGTH;
	private boolean lastSwitchReading;
	private ProfiledPIDController profiledPIDController;
	private static Extender instance;
	private GBSparkMax motor;
	
	public static Extender getInstance() {
		if (instance == null) {
			init();
		}
		return instance;
	}
	
	public static void init() {
		instance = new Extender();
	}
	
	private Extender() {
		motor = new GBSparkMax(RobotMap.telescopicArm.extender.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
		motor.getEncoder().setPosition(RobotMap.telescopicArm.extender.STARTING_POSITION);
		motor.config(new GBSparkMax.SparkMaxConfObject()
				.withPID(RobotMap.telescopicArm.extender.PID)
				.withPositionConversionFactor(RobotMap.telescopicArm.extender.POSITION_CONVERSION_FACTOR)
				.withVelocityConversionFactor(RobotMap.telescopicArm.extender.VELOCITY_CONVERSION_FACTOR)
				.withIdleMode(CANSparkMax.IdleMode.kBrake)
				.withRampRate(RobotMap.telescopicArm.extender.RAMP_RATE)
				.withCurrentLimit(RobotMap.telescopicArm.extender.CURRENT_LIMIT)
				.withVoltageComp(RobotMap.General.VOLTAGE_COMP_VAL)
		);
		motor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen).enableLimitSwitch(true);
		motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, RobotMap.telescopicArm.extender.FORWARD_LIMIT);
		
		profiledPIDController = new ProfiledPIDController(
				RobotMap.telescopicArm.extender.PID.getKp(),
				RobotMap.telescopicArm.extender.PID.getKi(),
				RobotMap.telescopicArm.extender.PID.getKd(),
				RobotMap.telescopicArm.extender.CONSTRAINTS
		);
		lastSwitchReading = getLimitSwitch();
	}
	
	public static ExtenderState getHypotheticalState(double lengthInMeters) {
		if (lengthInMeters > RobotMap.telescopicArm.extender.FORWARD_LIMIT || lengthInMeters < RobotMap.telescopicArm.extender.BACKWARDS_LIMIT) {
			return ExtenderState.OUT_OF_BOUNDS;
		} else if (lengthInMeters < RobotMap.telescopicArm.extender.MAX_ENTRANCE_LENGTH) {
			return ExtenderState.IN_WALL_LENGTH;
		} else if (lengthInMeters < RobotMap.telescopicArm.extender.MAX_LENGTH_IN_ROBOT) {
			return ExtenderState.IN_ROBOT_BELLY_LENGTH;
		} else {
			return ExtenderState.OPEN;
		}
	}
	
	
	public static double getFeedForward(double wantedSpeed, double wantedAcceleration, double elbowAngle) {
		return getStaticFeedForward(elbowAngle) +
				RobotMap.telescopicArm.extender.kS * Math.signum(wantedSpeed) +
				RobotMap.telescopicArm.extender.kV * wantedSpeed +
				RobotMap.telescopicArm.extender.kA * wantedAcceleration;
	}
	
	public static double getStaticFeedForward(double elbowAngle) {
		return Math.sin(elbowAngle - RobotMap.telescopicArm.elbow.STARTING_ANGLE_RELATIVE_TO_GROUND) * RobotMap.telescopicArm.extender.kG;
	}
	
	private void setLengthByPID(double lengthInMeters) {
		profiledPIDController.reset(getLength(),getVelocity());
		profiledPIDController.setGoal(lengthInMeters);
		double feedForward = getFeedForward(
				profiledPIDController.getSetpoint().velocity, (profiledPIDController.getSetpoint().velocity - motor.getEncoder().getVelocity()) / RoborioUtils.getCurrentRoborioCycle(), Elbow.getInstance().getAngle());
		SmartDashboard.putNumber("Extender FF", feedForward);
		motor.getPIDController().setReference(profiledPIDController.getSetpoint().velocity, CANSparkMax.ControlType.kVelocity, 0, feedForward);
	}
	
	public void moveTowardsLength(double lengthInMeters) {
		// going out of bounds should not be allowed
		if (getHypotheticalState(lengthInMeters) == ExtenderState.OUT_OF_BOUNDS) {
			System.err.println("arm Extender is trying to move OUT OF BOUNDS");
			stop();
			return;
		}
		// arm should not extend to open state when inside the belly (would hit chassis)
		if (Elbow.getInstance().getState() == Elbow.ElbowState.IN_BELLY && getHypotheticalState(lengthInMeters) == ExtenderState.OPEN) {
			setLengthByPID(RobotMap.telescopicArm.extender.MAX_ENTRANCE_LENGTH);
		}else if (Elbow.getInstance().getState() == Elbow.ElbowState.WALL_ZONE && getHypotheticalState(lengthInMeters) != ExtenderState.IN_WALL_LENGTH) {
			// arm should not extend too much in front of the wall
			stop();
		} else {
			setLengthByPID(lengthInMeters);
		}
	}
	
	
	public double getLength() {
		return motor.getEncoder().getPosition();
	}
	
	public ExtenderState getState() {
		return state;
	}

	public double getVelocity(){return motor.getEncoder().getVelocity();}
	
	/**
	 * @return the current value of the limit switch
	 */
	public boolean getLimitSwitch() {
		return motor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen).isPressed();
	}

	
	/**
	 * @return whether the switch changed status from last periodic this is useful to reset at
	 */
	public boolean didSwitchFlip(){
		return lastSwitchReading ^ getLimitSwitch();
	}
	
	public void resetLength(double position) {
			motor.getEncoder().setPosition(position);
	}

	public void resetLength(){
		motor.getEncoder().setPosition(0);
	}
	
	public void stop() {
		motor.set(0);
	}
	
	public enum ExtenderState {
		//see elbow state first
		//the state corresponding to IN_BELLY
		IN_ROBOT_BELLY_LENGTH,
		//the state corresponding to OUT_ROBOT
		OPEN,
		//the state corresponding to WALL_ZONE
		IN_WALL_LENGTH,
		// this state should not be possible and is either used to stop dangerous movement or to signal a bug
		OUT_OF_BOUNDS
	}
	
	public boolean isAtLength() {
		return profiledPIDController.atGoal();
	}
	
	public void setMotorVoltage(double voltage) {
		motor.setVoltage(voltage);
	}
	
	@Override
	public void periodic() {
		state = getHypotheticalState(getLength());
		lastSwitchReading = getLimitSwitch();
	}
	

}


