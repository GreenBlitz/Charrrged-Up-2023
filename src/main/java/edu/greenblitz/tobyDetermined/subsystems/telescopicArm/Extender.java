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
	
	private ProfiledPIDController profiledPIDController;
	private static Extender instance;
	private GBSparkMax motor;
	
	public static Extender getInstance() {
		if (instance == null) {
			init();
			SmartDashboard.putBoolean("extender initialized via getinstance", true);
		}
		return instance;
	}
	
	public static void init() {
		instance = new Extender();
	}
	
	private Extender() {
		motor = new GBSparkMax(RobotMap.TelescopicArm.Extender.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
		motor.getEncoder().setPosition(RobotMap.TelescopicArm.Extender.STARTING_LENGTH);
		motor.config(new GBSparkMax.SparkMaxConfObject()
				.withPID(RobotMap.TelescopicArm.Extender.PID)
				.withPositionConversionFactor(RobotMap.TelescopicArm.Extender.POSITION_CONVERSION_FACTOR)
				.withVelocityConversionFactor(RobotMap.TelescopicArm.Extender.VELOCITY_CONVERSION_FACTOR)
				.withIdleMode(CANSparkMax.IdleMode.kBrake)
				.withRampRate(RobotMap.TelescopicArm.Extender.RAMP_RATE)
				.withCurrentLimit(RobotMap.TelescopicArm.Extender.CURRENT_LIMIT)
				.withVoltageComp(RobotMap.General.VOLTAGE_COMP_VAL)
		);
		motor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen).enableLimitSwitch(true);
		motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, RobotMap.TelescopicArm.Extender.FORWARD_LIMIT);
		
		profiledPIDController = new ProfiledPIDController(
				RobotMap.TelescopicArm.Extender.PID.getKp(),
				RobotMap.TelescopicArm.Extender.PID.getKi(),
				RobotMap.TelescopicArm.Extender.PID.getKd(),
				RobotMap.TelescopicArm.Extender.CONSTRAINTS

		);
		profiledPIDController.setTolerance(RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE);
		lastSwitchReading = getLimitSwitch();
	}
	
	public static ExtenderState getHypotheticalState(double lengthInMeters) {
		if (lengthInMeters > RobotMap.TelescopicArm.Extender.FORWARD_LIMIT || lengthInMeters < RobotMap.TelescopicArm.Extender.BACKWARDS_LIMIT) {
			return ExtenderState.OUT_OF_BOUNDS;
		} else if (lengthInMeters < RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH) {
			return ExtenderState.IN_WALL_LENGTH;
		} else if (lengthInMeters < RobotMap.TelescopicArm.Extender.MAX_LENGTH_IN_ROBOT) {
			return ExtenderState.IN_ROBOT_BELLY_LENGTH;
		} else {
			return ExtenderState.OPEN;
		}
	}
	
	
	public static double getFeedForward(double wantedSpeed, double wantedAcceleration, double elbowAngle) {
		return getStaticFeedForward(elbowAngle) +
				RobotMap.TelescopicArm.Extender.kS * Math.signum(wantedSpeed) +
				RobotMap.TelescopicArm.Extender.kV * wantedSpeed +
				RobotMap.TelescopicArm.Extender.kA * wantedAcceleration;
	}
	
	public static double getStaticFeedForward(double elbowAngle) {
		return Math.sin(elbowAngle - RobotMap.TelescopicArm.Elbow.STARTING_ANGLE_RELATIVE_TO_GROUND) * RobotMap.TelescopicArm.Extender.kG;
	}
	
	private void setLengthByPID(double lengthInMeters) {
		profiledPIDController.reset(getLength());
		profiledPIDController.setGoal(lengthInMeters);
		double feedForward = getFeedForward(
				profiledPIDController.getSetpoint().velocity, (profiledPIDController.getSetpoint().velocity - motor.getEncoder().getVelocity()) / RoborioUtils.getCurrentRoborioCycle(), edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.getInstance().getAngle());
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
		if (edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.getInstance().getState() == edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.ElbowState.IN_BELLY && getHypotheticalState(lengthInMeters) == ExtenderState.OPEN) {
			setLengthByPID(RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH);
		}else if (edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.getInstance().getState() == edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.ElbowState.WALL_ZONE && getHypotheticalState(lengthInMeters) != ExtenderState.IN_WALL_LENGTH) {
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
	
	/**
	 * @return the current value of the limit switch
	 */
	public boolean getLimitSwitch() {
		return motor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen).isPressed();
	}
	
	private boolean lastSwitchReading;
	
	/**
	 * @return whether the switch changed status from last periodic this is useful to reset at
	 */
	public boolean didSwitchFlip(){
		return lastSwitchReading ^ getLimitSwitch();
	}
	
	public void resetLength() {
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
	
	public boolean isAtLength(double wantedLengthInMeters) {
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


