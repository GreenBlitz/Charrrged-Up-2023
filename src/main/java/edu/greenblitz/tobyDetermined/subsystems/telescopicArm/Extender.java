package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.greenblitz.tobyDetermined.subsystems.Dashboard;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.RoborioUtils;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Extender.FORWARDS_LENGTH_TOLERANCE;

public class Extender extends GBSubsystem {
	private static Extender instance;
	private static Extender.ExtenderState state = Extender.ExtenderState.IN_ROBOT_BELLY_LENGTH;
	public GBSparkMax motor;
	private boolean debug = false;
	private ProfiledPIDController profileGenerator; // this does not actually use the pid controller only the setpoint
	private double lastSpeed;
	private double goalLength;
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
		motor.getReverseLimitSwitch(RobotMap.TelescopicArm.Extender.SWITCH_TYPE).enableLimitSwitch(true);
		motor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
		motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, RobotMap.TelescopicArm.Extender.FORWARD_LIMIT);
		motor.setIdleMode(CANSparkMax.IdleMode.kBrake);

		profileGenerator = new ProfiledPIDController(
				0,0,0,
				RobotMap.TelescopicArm.Extender.CONSTRAINTS
		);


		profileGenerator.setTolerance(RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE);

		lastSpeed = 0;
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
		Dashboard.getInstance().armWidget.setLength(getLength());
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
		
		double gravPart = getStaticFeedForward(elbowAngle);
		double statPart = RobotMap.TelescopicArm.Extender.kS * Math.signum(wantedSpeed);
		double velPart = RobotMap.TelescopicArm.Extender.kV * wantedSpeed;
		double accPart =RobotMap.TelescopicArm.Extender.kA * wantedAcceleration;
		SmartDashboard.putNumber("grav", gravPart);
		SmartDashboard.putNumber("stat", statPart);
		SmartDashboard.putNumber("vel", velPart);
		SmartDashboard.putNumber("accel", accPart);
		return  gravPart+statPart+velPart+accPart;
		
	}

	public static double getStaticFeedForward(double elbowAngle) {
		return Math.sin(elbowAngle + RobotMap.TelescopicArm.Elbow.STARTING_ANGLE_RELATIVE_TO_GROUND) * RobotMap.TelescopicArm.Extender.kG;
	}

	private void setLengthByPID(double lengthInMeters, double feedForward) {
		goalLength = lengthInMeters;
		motor.getPIDController().setReference(lengthInMeters, CANSparkMax.ControlType.kPosition, 0, feedForward);
		SmartDashboard.putNumber("powah", feedForward);
		SmartDashboard.putNumber("current velocity", getVelocity());
		debugLastFF = feedForward;
	}

	public void moveTowardsLength(double lengthInMeters) {
		moveTowardsLength(lengthInMeters, 0);
	}


	public void moveTowardsLength(double lengthInMeters, double feedforward) {
        setLengthByPID(getLegalGoalLength(lengthInMeters), feedforward);
	}

	public double getLegalGoalLength(double wantedLength){
		SmartDashboard.putString("hypothetical extender state", getHypotheticalState(wantedLength).toString());
		// going out of bounds should not be allowed
		if (getHypotheticalState(wantedLength) == ExtenderState.OUT_OF_BOUNDS) {
			Console.log("OUT OF BOUNDS", "arm Extender is trying to move OUT OF BOUNDS" );
			if (wantedLength < RobotMap.TelescopicArm.Extender.BACKWARDS_LIMIT){
				return (RobotMap.TelescopicArm.Extender.BACKWARDS_LIMIT);
			} else {
				return (RobotMap.TelescopicArm.Extender.EXTENDED_LENGTH);
			}
		}
		
		// arm should not extend to open state when inside the belly (would hit chassis)
		else if (Elbow.getInstance().getState() == Elbow.ElbowState.IN_BELLY && getHypotheticalState(wantedLength) == ExtenderState.OPEN) {
			return (RobotMap.TelescopicArm.Extender.MAX_LENGTH_IN_ROBOT);
		}else if (Elbow.getInstance().getState() == Elbow.ElbowState.WALL_ZONE && getHypotheticalState(wantedLength) != ExtenderState.IN_WALL_LENGTH) {
			// arm should not extend too much in front of the wall
			return (RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH);
		} else {
			return (wantedLength);
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
		return motor.getReverseLimitSwitch(RobotMap.TelescopicArm.Extender.SWITCH_TYPE).isPressed();
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
		double lengthError = wantedLength - getLength();
		return lengthError > -FORWARDS_LENGTH_TOLERANCE  && lengthError < RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE;
		//makes it so the arm can only be too short, so it can always pass the state line
	}

	public boolean isAtLength() {
		SmartDashboard.putNumber("extender goal", profileGenerator.getGoal().position);
		return isAtLength(profileGenerator.getGoal().position);
	}

	public boolean isNotMoving(){
		return Math.abs(getVelocity()) < RobotMap.TelescopicArm.Extender.VELOCITY_TOLERANCE;
	}

	public void setMotorVoltage(double voltage) {
		motor.setVoltage(voltage);
	}

	public PIDObject getPID(){
		return new PIDObject().withKp(motor.getPIDController().getP()).withKi(motor.getPIDController().getI()).withKd(motor.getPIDController().getD());
	}
	
	public double getGoalLength() {
		return goalLength;
	}
}


