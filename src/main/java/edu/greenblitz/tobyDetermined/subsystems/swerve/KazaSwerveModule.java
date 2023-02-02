package edu.greenblitz.tobyDetermined.subsystems.swerve;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.GBMath;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;

import static edu.greenblitz.tobyDetermined.RobotMap.General.Motors.NEO_PHYSICAL_TICKS_TO_RADIANS;

public class KazaSwerveModule implements SwerveModule {

	public double targetAngle;
	public double targetVel;
	private GBSparkMax angleMotor;
	private GBSparkMax linearMotor;
	private AnalogInput lamprey;
	private SimpleMotorFeedforward feedforward;


	public KazaSwerveModule(int angleMotorID, int linearMotorID, int lampreyID, boolean linInverted) {
		//SET ANGLE MOTOR
		angleMotor = new GBSparkMax(angleMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
		angleMotor.config(RobotMap.Swerve.KazaSwerve.baseAngConfObj);
		angleMotor.getPIDController().setPositionPIDWrappingEnabled(true);
		angleMotor.getPIDController().setPositionPIDWrappingMaxInput(2* Math.PI);
		angleMotor.getPIDController().setPositionPIDWrappingMinInput(0);

		linearMotor = new GBSparkMax(linearMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
		linearMotor.config(RobotMap.Swerve.KazaSwerve.baseLinConfObj.withInverted(linInverted));

		lamprey = new AnalogInput(lampreyID);
		lamprey.setAverageBits(RobotMap.Swerve.Pegaswerve.LAMPREY_AVERAGE_BITS);
		this.feedforward = new SimpleMotorFeedforward(RobotMap.Swerve.KazaSwerve.ks, RobotMap.Swerve.KazaSwerve.kv, RobotMap.Swerve.KazaSwerve.ka);

	}


	public KazaSwerveModule(KazaSwerveModuleConfigObject KazaModuleConfigObject) {
		this(KazaModuleConfigObject.angleMotorID,
				KazaModuleConfigObject.linearMotorID,
				KazaModuleConfigObject.AbsoluteEncoderID,
				KazaModuleConfigObject.linInverted);
	}

	/**
	 * gets a target angle in radians, sets the internal PIDController to the shortest route to the angle
	 * relative to the encoder module angle
	 */
	@Override
	public void rotateToAngle(double angle) {
		angleMotor.getPIDController().setReference(angle, ControlType.kPosition);
		targetAngle = angle;
	}

	/**
	 * get the module angle by radians
	 */
	@Override
	public double getModuleAngle() {
		return angleMotor.getEncoder().getPosition();
	}

	@Override
	public double getCurrentVelocity() {
		return (linearMotor.getEncoder().getVelocity());
	}

	@Override
	public double getCurrentMeters() {
		return linearMotor.getEncoder().getPosition();
	}

	@Override
	public SwerveModulePosition getCurrentPosition() {
		return new SwerveModulePosition(getCurrentMeters(),new Rotation2d(getModuleAngle()));
	}


	/**
	 * resetEncoderToValue - reset the angular encoder to RADIANS
	 */
	@Override
	public void resetEncoderToValue(double angle) {
		angleMotor.getEncoder().setPosition(angle);
	}

	@Override
	public void resetEncoderToValue() {
		angleMotor.getEncoder().setPosition(0);
	}

	@Override
	public void resetEncoderByAbsoluteEncoder(SwerveChassis.Module module) {
		resetEncoderToValue(Calibration.CALIBRATION_DATASETS.get(module).get(getAbsoluteEncoderValue()) * NEO_PHYSICAL_TICKS_TO_RADIANS/ RobotMap.Swerve.KazaSwerve.ANG_GEAR_RATIO);
	}

	@Override
	public void configLinPID(PIDObject pidObject) {
		linearMotor.configPID(pidObject);
	}

	@Override
	public void configAnglePID(PIDObject pidObject) {
		angleMotor.configPID(pidObject);
	}

	@Override
	public void setLinSpeed(double speed) {
		linearMotor.getPIDController().setReference(speed, ControlType.kVelocity, 0, feedforward.calculate(speed));

	}

	@Override
	public void stop() {
		angleMotor.set(0);
		linearMotor.set(0);
	}

	@Override
	public double getTargetAngle() {
		return targetAngle;
	}

	@Override
	public double getTargetVel() {
		return targetVel;
	}

	//only for debugging

	@Override
	public SwerveModuleState getModuleState() {
		return new SwerveModuleState(getCurrentVelocity(), new Rotation2d(this.getModuleAngle()));
	}

	@Override
	public boolean isAtAngle(double targetAngleInRads, double tolerance) {
		double currentAngleInRads = getModuleAngle();
		return GBMath.absoluteModulo((currentAngleInRads - targetAngleInRads), (2 * Math.PI)) < tolerance
				|| GBMath.absoluteModulo((targetAngleInRads - currentAngleInRads), (2 * Math.PI)) < tolerance;
	}


	@Override
	public boolean isAtAngle(double tolerance) {
		return isAtAngle(targetAngle, tolerance);
	}

	/**
	 * sets to module to be at the given module state
	 */
	@Override
	public void setModuleState(SwerveModuleState moduleState) {
		setLinSpeed(moduleState.speedMetersPerSecond);
		rotateToAngle(moduleState.angle.getRadians());
	}

	/**
	 * get the lamprey's angle raw voltage
	 */
	@Override
	public double getAbsoluteEncoderValue() {
		return lamprey.getVoltage();
	}

	@Override
	public void setRotPowerOnlyForCalibrations(double power) {
		angleMotor.set(power);
	}

	@Override
	public void setLinPowerOnlyForCalibrations(double power) {
		linearMotor.set(power);
	}

	@Override
	public void setLinIdleModeBrake() {
		linearMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
	}

	@Override
	public void setLinIdleModeCoast() {
		linearMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
	}

	public static class KazaSwerveModuleConfigObject {
		private int angleMotorID;
		private int linearMotorID;
		private int AbsoluteEncoderID;
		private boolean linInverted;


		public KazaSwerveModuleConfigObject(int angleMotorID, int linearMotorID, int AbsoluteEncoderID, boolean linInverted) {
			this.angleMotorID = angleMotorID;
			this.linearMotorID = linearMotorID;
			this.AbsoluteEncoderID = AbsoluteEncoderID;
			this.linInverted = linInverted;
		}
	}


}