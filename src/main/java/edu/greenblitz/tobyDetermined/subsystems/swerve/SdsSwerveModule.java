package edu.greenblitz.tobyDetermined.subsystems.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.motors.GBFalcon;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.util.datalog.BooleanLogEntry;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.util.datalog.StringLogEntry;
import edu.greenblitz.tobyDetermined.subsystems.logger;

public class SdsSwerveModule implements SwerveModule {
	public double targetAngle;
	public double targetVel;
	private GBFalcon angleMotor;
	private GBFalcon linearMotor;
	private DutyCycleEncoder magEncoder;
	private SimpleMotorFeedforward feedforward;

	private DataLog log;
	private DoubleLogEntry linearMotorVoltagelog;
	private DoubleLogEntry angleMotorVoltagelog;
	private DoubleLogEntry anglelog;
	private DoubleLogEntry velocitylog;
	
	public SdsSwerveModule(int angleMotorID, int linearMotorID, int AbsoluteEncoderID, boolean linInverted, double magEncoderOffset) {
		//SET ANGLE MOTO
		angleMotor = new GBFalcon(angleMotorID);
		angleMotor.config(new GBFalcon.FalconConfObject(RobotMap.Swerve.SdsSwerve.baseAngConfObj));
		
		
		linearMotor = new GBFalcon(linearMotorID);
		linearMotor.config(new GBFalcon.FalconConfObject(RobotMap.Swerve.SdsSwerve.baseLinConfObj).withInverted(linInverted));
		
		magEncoder = new DutyCycleEncoder(AbsoluteEncoderID);
		this.magEncoder.setPositionOffset(magEncoderOffset);
		SmartDashboard.putNumber("lol", magEncoder.getPositionOffset());
		
		this.feedforward = new SimpleMotorFeedforward(RobotMap.Swerve.ks, RobotMap.Swerve.kv, RobotMap.Swerve.ka);
		
		log = logger.getInstance().get_log();
		this.linearMotorVoltagelog = new DoubleLogEntry(this.log, "/SwerveModule/LowLevel/LinearMotorVoltage");
		this.angleMotorVoltagelog = new DoubleLogEntry(this.log, "/SwerveModule/LowLevel/AngleMotorVoltage");
		this.anglelog = new DoubleLogEntry(this.log, "/SwerveModule/HighLevel/Angle");
		this.velocitylog = new DoubleLogEntry(this.log, "/SwerveModule/HighLevel/Velocity");
		
	}
	
	public SdsSwerveModule(SdsSwerveModuleConfigObject SdsModuleConfigObject) {
		this(SdsModuleConfigObject.angleMotorID,
				SdsModuleConfigObject.linearMotorID,
				SdsModuleConfigObject.AbsoluteEncoderID,
				SdsModuleConfigObject.linInverted,
				SdsModuleConfigObject.magEncoderOffset);
		
		log = logger.getInstance().get_log();
		this.linearMotorVoltagelog = new DoubleLogEntry(this.log, "/SwerveModule/LowLevel/LinearMotorVoltage");
		this.angleMotorVoltagelog = new DoubleLogEntry(this.log, "/SwerveModule/LowLevel/AngleMotorVoltage");
		this.anglelog = new DoubleLogEntry(this.log, "/SwerveModule/HighLevel/Angle");
		this.velocitylog = new DoubleLogEntry(this.log, "/SwerveModule/HighLevel/Velocity");

		
	}
	
	public static double convertRadsToTicks(double angInRads) {
		return angInRads / RobotMap.Swerve.SdsSwerve.angleTicksToRadians;
	}
	
	public static double convertTicksToRads(double angInTicks) {
		return angInTicks * RobotMap.Swerve.SdsSwerve.angleTicksToRadians;
	}

	public static double convertMetersToTicks(double distanceInMeters) {
		return distanceInMeters / RobotMap.Swerve.SdsSwerve.linTicksToMeters;
	}

	public static double convertTicksToMeters(double angInTicks){
		return angInTicks * RobotMap.Swerve.SdsSwerve.linTicksToMeters;
	}
	
	/**
	 * @param angleInRads - angle to turn to in radians
	 */
	@Override
	public void rotateToAngle(double angleInRads) {
		double diff = Math.IEEEremainder(angleInRads - getModuleAngle(), 2 * Math.PI);
		diff -= diff > Math.PI ? 2 * Math.PI : 0;
		angleInRads = getModuleAngle() + diff;

		angleMotor.set(ControlMode.Position, convertRadsToTicks(angleInRads));
		
		targetAngle = angleInRads;
	}
	
	/**
	 * @return get the module angle in radians
	 */
	@Override
	//maybe not after gear ratio plz check ~ noam
	public double getModuleAngle() {
		return convertTicksToRads(angleMotor.getSelectedSensorPosition());
	}
	
	/**
	 * @return returns the current linear motor velocity
	 */
	@Override
	public double getCurrentVelocity() {
		return linearMotor.getSelectedSensorVelocity() * RobotMap.Swerve.SdsSwerve.linTicksToMetersPerSecond;
	}

	@Override
	public double getCurrentMeters() {
		return convertTicksToMeters(linearMotor.getSelectedSensorPosition()); //TODO make sure its true
	}

	@Override
	public SwerveModulePosition getCurrentPosition() {
		return new SwerveModulePosition(getCurrentMeters(),new Rotation2d(getModuleAngle()));
	}

	/**
	 * @param angleInRads - Position to set for the angular encoder (in raw sensor units).
	 */
	@Override
	public void resetEncoderToValue(double angleInRads) {
		angleMotor.setSelectedSensorPosition(convertRadsToTicks(angleInRads));
	}
	
	/**
	 * sets the encoder current position to 0;
	 */
	@Override
	public void resetEncoderToValue() {
		angleMotor.setSelectedSensorPosition(0);
	}
	
	@Override
	public void resetEncoderByAbsoluteEncoder(SwerveChassis.Module module) {
		angleMotor.setSelectedSensorPosition(getAbsoluteEncoderValue() * RobotMap.Swerve.SdsSwerve.magEncoderTicksToFalconTicks);
	}
	
	@Override
	public void configLinPID(PIDObject pidObject) {
		linearMotor.configPID(pidObject);
	}
	
	@Override
	public void configAnglePID(PIDObject pidObject) {
		angleMotor.configPID(pidObject);
	}
	
	/**
	 * @param speed - double of the wanted speed (m/s) of the linear motor, uses PID and feedForward
	 */
	@Override
	public void setLinSpeed(double speed) {
		linearMotor.set(
				TalonFXControlMode.Velocity,
				speed / RobotMap.Swerve.SdsSwerve.linTicksToMetersPerSecond,
				DemandType.ArbitraryFeedForward,
				feedforward.calculate(speed));
	}
	
	/**
	 * stops the angular and linear module.
	 */
	@Override
	public void stop() {
		linearMotor.set(ControlMode.PercentOutput, 0);
		angleMotor.set(ControlMode.PercentOutput, 0);
	}
	
	/**
	 * @return returns the target angle of the angular motor
	 */
	@Override
	public double getTargetAngle() {
		return targetAngle;
	}
	
	/**
	 * @return get the linear motor's target velocity
	 */
	@Override
	public double getTargetVel() {
		return targetVel;
	}
	
	/**
	 * @return return the @class {@link SwerveModuleState} object of the angle and velocity of the module
	 */
	@Override
	public SwerveModuleState getModuleState() {
		return new SwerveModuleState(
				getCurrentVelocity(),
				new Rotation2d(getModuleAngle())
		);
	}
	
	/**
	 * @param moduleState - @class {@link SwerveModuleState} to set the module to (angle and velocity)
	 */
	@Override
	public void setModuleState(SwerveModuleState moduleState) {
		setLinSpeed(moduleState.speedMetersPerSecond);
		rotateToAngle(moduleState.angle.getRadians());
	}
	
	/**
	 * get the magEncoder's angle value in rotations
	 */
	@Override
	public double getAbsoluteEncoderValue() {
		return magEncoder.get();
	}
	
	/**
	 * @param power - double between 1 to -1 to set the percentage of the motors rotation
	 */
	@Override
	public void setRotPowerOnlyForCalibrations(double power) {
		angleMotor.set(ControlMode.PercentOutput, power);
	}
	
	@Override
	public void setLinPowerOnlyForCalibrations(double power) {
		linearMotor.set(ControlMode.PercentOutput, power);
	}

	@Override
	public void setLinIdleModeBrake() {
		linearMotor.setNeutralMode(NeutralMode.Brake);
	}

	@Override
	public void setLinIdleModeCoast() {
		linearMotor.setNeutralMode(NeutralMode.Coast);
	}

	public static class SdsSwerveModuleConfigObject {
		private int angleMotorID;
		private int linearMotorID;
		private int AbsoluteEncoderID;
		private boolean linInverted;
		private double magEncoderOffset;
		
		public SdsSwerveModuleConfigObject(int angleMotorID, int linearMotorID, int AbsoluteEncoderID, boolean linInverted, double magEncoderOffset) {
			this.angleMotorID = angleMotorID;
			this.linearMotorID = linearMotorID;
			this.AbsoluteEncoderID = AbsoluteEncoderID;
			this.linInverted = linInverted;
			this.magEncoderOffset = magEncoderOffset;
		}
	}

	@Override
	public void hightLevelLog(String moduleName){
		anglelog.append(getModuleAngle());
		velocitylog.append(getCurrentVelocity());
	}

	@Override
	public void lowLevelLog(String moduleName){
		linearMotorVoltagelog.append(linearMotor.getBusVoltage());
		angleMotorVoltagelog.append(angleMotor.getBusVoltage());
	}
}
