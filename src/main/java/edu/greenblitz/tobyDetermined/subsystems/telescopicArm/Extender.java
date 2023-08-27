package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Extender.*;

public class Extender extends GBSubsystem {
	private static Extender instance;
	private static Extender.ExtenderState state = Extender.ExtenderState.IN_ROBOT_BELLY_LENGTH;
	public GBSparkMax motor;
	private ProfiledPIDController profileGenerator; // this does not actually use the pid controller only the setpoint
	private double lastSpeed;
	private double goalLength;
	private Debouncer debouncer;
	private boolean holdPosition =false;
	private boolean didReset;
	private double speed;
	private double angle;

	public static Extender getInstance() {
		init();
		return instance;
	}
	
	public static void init() {
		if (instance == null) {
			instance = new Extender();
		}
	}

	public boolean DoesSensorExist = true;
	private Extender() {
		motor = new GBSparkMax(RobotMap.TelescopicArm.Extender.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
		motor.config(RobotMap.TelescopicArm.Extender.EXTENDER_CONFIG_OBJECT);
		motor.setSmartCurrentLimit(50,EXTENDER_CONFIG_OBJECT.getCurrentLimit());
		motor.getEncoder().setPosition(RobotMap.TelescopicArm.Extender.STARTING_LENGTH);
		motor.getReverseLimitSwitch(RobotMap.TelescopicArm.Extender.SWITCH_TYPE).enableLimitSwitch(DoesSensorExist);
		motor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
		motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, RobotMap.TelescopicArm.Extender.FORWARD_LIMIT);
		motor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, false);
		motor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, BACKWARDS_LIMIT);
		motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
		profileGenerator = new ProfiledPIDController(
				0,0,0,
				RobotMap.TelescopicArm.Extender.CONSTRAINTS
		);

		profileGenerator.setTolerance(RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE);

		lastSpeed = 0;
		didReset = false;
		debouncer = new Debouncer(RobotMap.TelescopicArm.Extender.DEBOUNCE_TIME_FOR_LIMIT_SWITCH, Debouncer.DebounceType.kBoth);
		accTimer = new Timer();
		accTimer.start();
	}

	private Timer accTimer;

	public void unsafeSetGoalLengthByPid(double length){
		goalLength = length;
	}

	public void debugSetPower(double power){
		motor.set(power);
		holdPosition = false;
	}
	
	@Override
	public void periodic() {
		state = getHypotheticalState(getLength());
		SmartDashboard.putBoolean("holdPosition", holdPosition);
		SmartDashboard.putNumber("voltage",getVolt());
		SmartDashboard.putNumber("velocity",getVelocity());
		SmartDashboard.putNumber("position",getLength());
		SmartDashboard.putNumber("current", motor.getOutputCurrent());

		if (accTimer.advanceIfElapsed(0.15)) {
			SmartDashboard.putNumber("curr acc",
					(getVelocity() - lastSpeed) / (0.15 + accTimer.get())
			);
			lastSpeed = getVelocity();
		}



		
		if (holdPosition) {
			motor.setVoltage(getStaticFeedForward(Elbow.getInstance().getAngleRadians()));
		}
	}

	public void updatePIDController(PIDObject pidObject){
		motor.configPID(pidObject);
	}
	public static ExtenderState getHypotheticalState(double lengthInMeters) {
		if (lengthInMeters < ExtenderState.REVERSE_OUT_OF_BOUNDS.maxLength) {
			return ExtenderState.REVERSE_OUT_OF_BOUNDS;
		} else if (lengthInMeters < ExtenderState.IN_WALL_LENGTH.maxLength) {
			return ExtenderState.IN_WALL_LENGTH;
		} else if (lengthInMeters < ExtenderState.IN_ROBOT_BELLY_LENGTH.maxLength) {
			return ExtenderState.IN_ROBOT_BELLY_LENGTH;
		} else if(lengthInMeters < ExtenderState.OPEN.maxLength){
			return ExtenderState.OPEN;
		} else{
			return ExtenderState.FORWARD_OUT_OF_BOUNDS;
		}
	}

	public static double getStaticFeedForward(double elbowAngle) {
		return Math.sin(elbowAngle + RobotMap.TelescopicArm.Elbow.STARTING_ANGLE_RELATIVE_TO_GROUND) * RobotMap.TelescopicArm.Extender.kG;
	}
	
	public double getVolt(){
		return motor.getAppliedOutput() * Battery.getInstance().getCurrentVoltage();
	}

	public double getLegalGoalLength(double wantedLength){
		// going out of bounds should not be allowed
		if (getHypotheticalState(wantedLength) == ExtenderState.FORWARD_OUT_OF_BOUNDS) {
			Console.log("OUT OF BOUNDS", "arm Extender is trying to move OUT OF BOUNDS" );
			return (RobotMap.TelescopicArm.Extender.EXTENDED_LENGTH - RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE);
		} else if (getHypotheticalState(wantedLength) == ExtenderState.REVERSE_OUT_OF_BOUNDS) {
			Console.log("OUT OF BOUNDS", "arm Extender is trying to move OUT OF BOUNDS" );
			return (RobotMap.TelescopicArm.Extender.BACKWARDS_LIMIT + RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE);
		}

		// arm should not extend to open state when inside the belly (would hit chassis)
		else if (Elbow.getInstance().getState() == Elbow.ElbowState.IN_BELLY && getHypotheticalState(wantedLength).longerThan( ExtenderState.IN_ROBOT_BELLY_LENGTH)) {
			return (RobotMap.TelescopicArm.Extender.MAX_LENGTH_IN_ROBOT - RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE);
		}else if (Elbow.getInstance().getState() == Elbow.ElbowState.WALL_ZONE && getHypotheticalState(wantedLength).longerThan(ExtenderState.IN_WALL_LENGTH)) {
			// arm should not extend too much in front of the wall
			return (RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH - RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE);
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

	public boolean DidReset(){
		return didReset;
	}
	/**
	 * @return the current value of the limit switch
	 */
	public boolean getLimitSwitch() {
		return debouncer.calculate(motor.getReverseLimitSwitch(RobotMap.TelescopicArm.Extender.SWITCH_TYPE).isPressed());
	}
	public void resetLength() {
		resetLength(0);
		didReset = true;
		enableReverseLimit();
	}

	public void resetLength(double position) {
		motor.getEncoder().setPosition(position);
	}


	public void stop() {
		motor.set(0);
		holdPosition = true;
	}


	public boolean isAtLength(double wantedLength){
		double lengthError = wantedLength - getLength();
		return lengthError > -FORWARDS_LENGTH_TOLERANCE  && lengthError < RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE;
		//makes it so the arm can only be too short, so it can always pass the state line
	}

	public enum ExtenderState{
		//see elbow state first
		//the state corresponding to WALL_ZONE
		IN_WALL_LENGTH(MAX_ENTRANCE_LENGTH),
		//the state corresponding to IN_BELLY
		IN_ROBOT_BELLY_LENGTH(MAX_LENGTH_IN_ROBOT),
		//the state corresponding to OUT_ROBOT
		OPEN(FORWARD_LIMIT),
		// this state should not be possible and is either used to stop dangerous movement or to signal a bug
		REVERSE_OUT_OF_BOUNDS(BACKWARDS_LIMIT),
		FORWARD_OUT_OF_BOUNDS(Double.POSITIVE_INFINITY);

		private double maxLength;
		ExtenderState(double maxLength){
			this.maxLength = maxLength;
		}

		public void setMaxLength(double maxLength){
			this.maxLength = maxLength;
		}

		public boolean longerThan(ExtenderState other){
			return this.maxLength > other.maxLength;
		}

		public boolean shorterOrEqualTo(ExtenderState other){
			return !longerThan(other);
		}
		
		}

	public boolean isAtLength() {
		SmartDashboard.putNumber("extender goal", profileGenerator.getGoal().position);
		return isAtLength(profileGenerator.getGoal().position);
	}

	public boolean isNotMoving(){
		return Math.abs(getVelocity()) < RobotMap.TelescopicArm.Extender.VELOCITY_TOLERANCE;
	}

	public void setMotorVoltage(double voltage) {
		holdPosition = false;
		motor.set(voltage / Battery.getInstance().getCurrentVoltage());
	}

	public void setLinSpeed(double speed, double angle) {
		motor.getPIDController().setReference(speed, CANSparkMax.ControlType.kVelocity, 0, getStaticFeedForward(angle));

	}

	public PIDObject getPID(){
		return new PIDObject().withKp(motor.getPIDController().getP()).withKi(motor.getPIDController().getI()).withKd(motor.getPIDController().getD());
	}
	
	public double getGoalLength() {
		return goalLength;
	}
	
	public void enableReverseLimit(){
		motor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
	}
	
	public void disableReverseLimit(){
		motor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, false);
	}

	public void setIdleMode(CANSparkMax.IdleMode idleMode){
		motor.setIdleMode(idleMode);
	}
	
	public void disableAllLimits(){
		motor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, false);
		motor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, false);
		motor.getReverseLimitSwitch(SWITCH_TYPE).enableLimitSwitch(false);
	}
}


