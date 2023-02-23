package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxLimitSwitch;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.greenblitz.tobyDetermined.subsystems.Dashboard;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.RoborioUtils;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Extender extends GBSubsystem {
	private static Extender instance;
	private static Extender.ExtenderState state = Extender.ExtenderState.IN_ROBOT_BELLY_LENGTH;
	private GBSparkMax motor;
	private boolean debug = false;
	private ProfiledPIDController profileGenerator; // this does not actually use the pid controller only the setpoint
	private double lastSpeed;
	private boolean lastSwitchReading;
	private double debugLastFF;

	public static Extender getInstance() {
		init();
		return instance;
	}
	
	public static void init() {
		if (instance == null) {
			instance = new Extender();
		}
	}

	private Extender() {
		motor = new GBSparkMax(RobotMap.TelescopicArm.Extender.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
		motor.config(RobotMap.TelescopicArm.Extender.EXTENDER_CONFIG_OBJECT);
		motor.getEncoder().setPosition(RobotMap.TelescopicArm.Extender.STARTING_LENGTH);
		motor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen).enableLimitSwitch(true);
		motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, RobotMap.TelescopicArm.Extender.FORWARD_LIMIT);

		profileGenerator = new ProfiledPIDController(
				0,0,0,
				RobotMap.TelescopicArm.Extender.CONSTRAINTS
		);


		profileGenerator.setTolerance(RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE);

		lastSpeed = 0;
		lastSwitchReading = getLimitSwitch();
	}

	private void debugSoftLimit(){
		motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 0.3);
	}

	public void debugSetPower(double power){
		motor.set(power);
	}
	
	@Override
	public void periodic() {
		state = getHypotheticalState(getLength());
		lastSpeed = getVelocity();
		lastSwitchReading = getLimitSwitch();
//		updatePIDController(Dashboard.getInstance().getExtenderPID());
	}

	public void updatePIDController(PIDObject pidObject){
		motor.configPID(pidObject);
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
		profileGenerator.reset(getLength());
		profileGenerator.setGoal(lengthInMeters);
		double feedForward = getFeedForward(
				profileGenerator.getSetpoint().velocity, (profileGenerator.getSetpoint().velocity - lastSpeed) / RoborioUtils.getCurrentRoborioCycle(), Elbow.getInstance().getAngleRadians());
		motor.getPIDController().setReference(profileGenerator.getSetpoint().velocity, CANSparkMax.ControlType.kVelocity, 0, feedForward);
		SmartDashboard.putNumber("Extender FF", feedForward);
		debugLastFF = feedForward;
	}

	public void moveTowardsLength(double lengthInMeters) {
		// going out of bounds should not be allowed
		if (getHypotheticalState(lengthInMeters) == ExtenderState.OUT_OF_BOUNDS) {
			if (lengthInMeters < RobotMap.TelescopicArm.Extender.BACKWARDS_LIMIT){
				moveTowardsLength(RobotMap.TelescopicArm.Extender.BACKWARDS_LIMIT);
			} else if (lengthInMeters > RobotMap.TelescopicArm.Extender.FORWARD_LIMIT){
				moveTowardsLength(RobotMap.TelescopicArm.Extender.EXTENDED_LENGTH);
			}
			else {
				stop();
			}
			Console.log("OUT OF BOUNDS", "arm Extender is trying to move OUT OF BOUNDS" );
			return;
		}
		// arm should not extend to open state when inside the belly (would hit chassis)
		if (Elbow.getInstance().getState() == Elbow.ElbowState.IN_BELLY && getHypotheticalState(lengthInMeters) == ExtenderState.OPEN) {
			setLengthByPID(RobotMap.TelescopicArm.Extender.MAX_LENGTH_IN_ROBOT);
		}else if (Elbow.getInstance().getState() == Elbow.ElbowState.WALL_ZONE && getHypotheticalState(lengthInMeters) != ExtenderState.IN_WALL_LENGTH) {
			// arm should not extend too much in front of the wall
			setLengthByPID(RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH);
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

	public double getVelocity(){
		return motor.getEncoder().getVelocity();
	}
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

	public void resetLength() {
			resetLength(0);
	}

	public void resetLength(double position) {
		motor.getEncoder().setPosition(position);
	}

	public void stop() {
		motor.set(0);
	}

	public double getDebugLastFF(){
		return debugLastFF;
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

	public boolean isAtLength(double wantedLength){
		return Math.abs(profileGenerator.getGoal().position - wantedLength) >= RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE;
	}

	public boolean isAtLength() {
		return isAtLength(profileGenerator.getGoal().position);
	}

	public void setMotorVoltage(double voltage) {
		motor.setVoltage(voltage);
	}

	public PIDObject getPID(){
		return new PIDObject().withKp(motor.getPIDController().getP()).withKi(motor.getPIDController().getI()).withKd(motor.getPIDController().getD());
	}
}


