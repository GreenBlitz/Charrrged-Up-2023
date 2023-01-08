package edu.greenblitz.pegasus.subsystems.swerve;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.utils.GBMath;
import edu.greenblitz.pegasus.utils.PIDObject;
import edu.greenblitz.pegasus.utils.motors.GBFalcon;
import edu.greenblitz.pegasus.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SdsSwerveModule implements SwerveModule{
	public double targetAngle;
	public double targetVel;
	private GBFalcon angleMotor;
	private GBFalcon linearMotor;
	private DutyCycleEncoder magEncoder;
	private SimpleMotorFeedforward feedforward;

	public SdsSwerveModule(int angleMotorID, int linearMotorID, int AbsoluteEncoderID, boolean linInverted,double magEncoderOffset) {
		//SET ANGLE MOTO
		angleMotor = new GBFalcon(angleMotorID);
		angleMotor.config(new GBFalcon.FalconConfObject(RobotMap.Pegasus.Swerve.SdsSwerve.baseAngConfObj));


		linearMotor = new GBFalcon(linearMotorID);
		linearMotor.config(new GBFalcon.FalconConfObject(RobotMap.Pegasus.Swerve.SdsSwerve.baseLinConfObj).withInverted(linInverted));

		magEncoder = new DutyCycleEncoder(AbsoluteEncoderID);
		this.magEncoder.setPositionOffset(magEncoderOffset);
		SmartDashboard.putNumber("lol", magEncoder.getPositionOffset());

		this.feedforward = new SimpleMotorFeedforward(RobotMap.Pegasus.Swerve.ks, RobotMap.Pegasus.Swerve.kv, RobotMap.Pegasus.Swerve.ka);;
	}

	public SdsSwerveModule(SdsSwerveModuleConfigObject SdsModuleConfigObject){
		this(SdsModuleConfigObject.angleMotorID,
		SdsModuleConfigObject.linearMotorID,
		SdsModuleConfigObject.AbsoluteEncoderID,
		SdsModuleConfigObject.linInverted,
		SdsModuleConfigObject.magEncoderOffset);
	}

	public static class SdsSwerveModuleConfigObject{
		private int angleMotorID;
		private int linearMotorID;
		private int AbsoluteEncoderID;
		private boolean linInverted;
		private double magEncoderOffset;

		public SdsSwerveModuleConfigObject(int angleMotorID, int linearMotorID, int AbsoluteEncoderID, boolean linInverted,double magEncoderOffset){
			this.angleMotorID = angleMotorID;
			this.linearMotorID = linearMotorID;
			this.AbsoluteEncoderID = AbsoluteEncoderID;
			this.linInverted = linInverted;
			this.magEncoderOffset = magEncoderOffset;
		}
	}
	

	public static double convertRadsToTicks(double angInRads){
		return angInRads/RobotMap.Pegasus.Swerve.SdsSwerve.angleTicksToRadians;
	}
	public static double convertTicksToRads(double angInTicks){
		return angInTicks*RobotMap.Pegasus.Swerve.SdsSwerve.angleTicksToRadians;
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
	 * @param angleInRads - angle to turn to in radians
	 */
	@Override
	public void rotateToAngle(double angleInRads) {
		double diff = GBMath.modulo(angleInRads - getModuleAngle(), 2 * Math.PI);
		diff -= diff > Math.PI ? 2*Math.PI : 0;
		angleInRads = getModuleAngle() + diff;

		angleMotor.set(ControlMode.Position,convertRadsToTicks(angleInRads));

		targetAngle = angleInRads;
	}

	/**
	 * @return get the module angle in radians
	 */
	@Override
	//maybe not after gear ratio plz check ~ noam
	public double getModuleAngle() {
		return convertTicksToRads(angleMotor.getSelectedSensorPosition()) ;
	}

	/**
	 * @return returns the current linear motor velocity
	 */
	@Override
	public double getCurrentVelocity() {
		return linearMotor.getSelectedSensorVelocity() * RobotMap.Pegasus.Swerve.SdsSwerve.linTicksToMetersPerSecond;
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
		angleMotor.setSelectedSensorPosition(getAbsoluteEncoderValue() * RobotMap.Pegasus.Swerve.SdsSwerve.magEncoderTicksToFalconTicks);
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
				speed / RobotMap.Pegasus.Swerve.SdsSwerve.linTicksToMetersPerSecond,
				DemandType.ArbitraryFeedForward,
				feedforward.calculate(speed));
	}

	/**
	 * stops the angular and linear module.
	 */
	@Override
	public void stop() {
		linearMotor.set(ControlMode.PercentOutput,0);
		angleMotor.set(ControlMode.PercentOutput,0);
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
	 * get the magEncoder's angle value in rotations
	 * */
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
	public void setLinPowerOnlyForCalibrations(double power){
		linearMotor.set(ControlMode.PercentOutput, power);
	}
	
}
