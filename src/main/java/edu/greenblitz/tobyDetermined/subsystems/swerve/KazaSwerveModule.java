package edu.greenblitz.tobyDetermined.subsystems.swerve;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.PIDObject;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.util.datalog.BooleanLogEntry;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.util.datalog.StringLogEntry;

import edu.wpi.first.util.datalog.BooleanLogEntry;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.util.datalog.StringLogEntry;
import edu.greenblitz.tobyDetermined.subsystems.logger;

import static edu.greenblitz.tobyDetermined.RobotMap.General.Motors.NEO_PHYSICAL_TICKS_TO_RADIANS;

public class KazaSwerveModule implements SwerveModule {
	
	public double targetAngle;
	public double targetVel;
	private GBSparkMax angleMotor;
	private GBSparkMax linearMotor;
	private AnalogInput lamprey;
	private SimpleMotorFeedforward feedforward;

	private DataLog log;
	private DoubleLogEntry linearMotorVoltagelog;
	private DoubleLogEntry angleMotorVoltagelog;
	private DoubleLogEntry anglelog;
	private DoubleLogEntry velocitylog;
	
	
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
		lamprey.setAverageBits(2);
		this.feedforward = new SimpleMotorFeedforward(RobotMap.Swerve.ks, RobotMap.Swerve.kv, RobotMap.Swerve.ka);

		log = logger.getInstance().get_log();
		this.linearMotorVoltagelog = new DoubleLogEntry(this.log, "/SwerveModule/LowLevel/LinearMotorVoltage");
		this.angleMotorVoltagelog = new DoubleLogEntry(this.log, "/SwerveModule/LowLevel/AngleMotorVoltage");
		this.anglelog = new DoubleLogEntry(this.log, "/SwerveModule/HighLevel/Angle");
		this.velocitylog = new DoubleLogEntry(this.log, "/SwerveModule/HighLevel/Velocity");

		
	}
	
	
	public KazaSwerveModule(KazaSwerveModuleConfigObject KazaModuleConfigObject) {
		this(KazaModuleConfigObject.angleMotorID,
				KazaModuleConfigObject.linearMotorID,
				KazaModuleConfigObject.AbsoluteEncoderID,
				KazaModuleConfigObject.linInverted);

		log = logger.getInstance().get_log();
		this.linearMotorVoltagelog = new DoubleLogEntry(this.log, "/SwerveModule/LowLevel/LinearMotorVoltage");
		this.angleMotorVoltagelog = new DoubleLogEntry(this.log, "/SwerveModule/LowLevel/AngleMotorVoltage");
		this.anglelog = new DoubleLogEntry(this.log, "/SwerveModule/HighLevel/Angle");
		this.velocitylog = new DoubleLogEntry(this.log, "/SwerveModule/HighLevel/Velocity");

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