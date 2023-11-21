package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.Robot;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.littletonrobotics.junction.Logger;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Extender.*;

public class Extender extends GBSubsystem {

	private boolean doesSensorExists = true;
	private static Extender instance;
	private static Extender.ExtenderState state = Extender.ExtenderState.IN_ROBOT_BELLY_LENGTH;
 	private ProfiledPIDController profileGenerator; // this does not actually use the pid controller only the setpoint
	private double lastSpeed;
	private double goalLength;
	private Debouncer debouncer;
	private boolean holdPosition =false;
	private boolean didReset;
	private double speed;
	private double angle;

	private final IExtender extender;
	private final ExtenderInputsAutoLogged extenderInputs = new ExtenderInputsAutoLogged();
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
		extender = ExtenderFactory.create();
		extender.updateInputs(extenderInputs);

		this.profileGenerator = new ProfiledPIDController(
				extenderInputs.kP,
				extenderInputs.kI,
				extenderInputs.kD,
				CONSTRAINTS
		);
		this.profileGenerator.setTolerance(extenderInputs.tolerance);


		Logger.getInstance().recordOutput("Arm/ExtenderIdleMode","switched to: " +  CANSparkMax.IdleMode.kCoast.name());

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

	public void setPower(double power){
		extender.setPower(power);
		holdPosition = false;
	}

	@Override
	public void periodic() {
		state = getHypotheticalState(getLength());
		SmartDashboard.putBoolean("holdPosition", holdPosition);
		SmartDashboard.putNumber("voltage",getVolt());
		SmartDashboard.putNumber("velocity",getVelocity());
		SmartDashboard.putNumber("",getLength());

		if (accTimer.advanceIfElapsed(0.15)) {
			SmartDashboard.putNumber("curr acc",
					(getVelocity() - lastSpeed) / (0.15 + accTimer.get())
			);
			lastSpeed = getVelocity();
		}





		if (holdPosition) {
			extender.setVoltage(getStaticFeedForward(Elbow.getInstance().getAngleRadians()));
		}

		extender.updateInputs(extenderInputs);
		Logger.getInstance().processInputs("Extender", extenderInputs);


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
		return RobotMap.ROBOT_TYPE != Robot.RobotType.SIMULATION ?  Math.sin(elbowAngle + RobotMap.TelescopicArm.Elbow.STARTING_ANGLE_RELATIVE_TO_GROUND) * RobotMap.TelescopicArm.Extender.kG : 0;
	}

	public static double getDynamicFeedForward(double wantedVelocity,double elbowAngle) {
		return getStaticFeedForward(elbowAngle) + kV * wantedVelocity+ kS * Math.signum(wantedVelocity);
	}
	
	public double getVolt(){
		return extenderInputs.appliedOutput * Battery.getInstance().getCurrentVoltage();
	}

	public double getLegalGoalLength(double wantedLength){
		// going out of bounds should not be allowed
		if (getHypotheticalState(wantedLength) == ExtenderState.FORWARD_OUT_OF_BOUNDS) {
			Console.log("OUT OF BOUNDS", "arm Extender is trying to move OUT OF BOUNDS" );
			return (RobotMap.TelescopicArm.Extender.EXTENDED_LENGTH - profileGenerator.getPositionTolerance());
		} else if (getHypotheticalState(wantedLength) == ExtenderState.REVERSE_OUT_OF_BOUNDS) {
			Console.log("OUT OF BOUNDS", "arm Extender is trying to move OUT OF BOUNDS" );
			return (RobotMap.TelescopicArm.Extender.BACKWARDS_LIMIT + profileGenerator.getPositionTolerance());
		}

		// arm should not extend to open state when inside the belly (would hit chassis)
		else if (Elbow.getInstance().getState() == Elbow.ElbowState.IN_BELLY && getHypotheticalState(wantedLength).longerThan( ExtenderState.IN_ROBOT_BELLY_LENGTH)) {
			return (RobotMap.TelescopicArm.Extender.MAX_LENGTH_IN_ROBOT - profileGenerator.getPositionTolerance());
		}else if (Elbow.getInstance().getState() == Elbow.ElbowState.WALL_ZONE && getHypotheticalState(wantedLength).longerThan(ExtenderState.IN_WALL_LENGTH)) {
			// arm should not extend too much in front of the wall
			return (RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH - profileGenerator.getPositionTolerance());
		} else {
			return (wantedLength);
		}
	}


	public double getLength() {
		return extenderInputs.position;
	}

	public ExtenderState getState() {
		return state;
	}

	public double getVelocity(){
		return extenderInputs.velocity;
	}

	public boolean DidReset(){
		return didReset;
	}
	/**
	 * @return the current value of the limit switch
	 */
	public boolean getLimitSwitch() {
		return debouncer.calculate(extenderInputs.reverseLimitSwitchPressed);
	}
	public void resetLength() {
		resetLength(0);
		didReset = true;
		enableReverseLimit();
	}

	public void resetLength(double position) {
		extender.setPosition(position);
	}


	public void stop() {
		extender.setPower(0);
		holdPosition = true;
	}

	public void brake(){
		extender.setIdleMode(CANSparkMax.IdleMode.kBrake);
	}

	public boolean isAtLength(double wantedLength){
		double lengthError = wantedLength - getLength();
		return lengthError > -FORWARDS_LENGTH_TOLERANCE  && lengthError < profileGenerator.getPositionTolerance();
		//makes it so the arm can only be too short, so it can always pass the state line
	}

	public boolean isSensorExists() {
		return doesSensorExists;
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
		extender.setPower(voltage / Battery.getInstance().getCurrentVoltage());
	}

	public PIDObject getPID(){
		return new PIDObject().withKp(extenderInputs.kP).withKi(extenderInputs.kI).withKd(extenderInputs.kI);
	}

	public double getGoalLength() {
		return goalLength;
	}

	public void enableReverseLimit(){
		extender.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
	}

	public void disableReverseLimit(){
		extender.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, false);
	}

	public void setIdleMode(CANSparkMax.IdleMode idleMode){
		extender.setIdleMode(idleMode);
		Logger.getInstance().recordOutput("Arm/ExtenderIdleMode", "switched to: " + idleMode.name());
	}

	public void disableAllLimits(){
		extender.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, false);
		extender.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, false);
		extender.enableBackSwitchLimit(false);
	}
	public void setGoalLength (double goalLength){
		this.goalLength = goalLength;
	}
}


