package edu.greenblitz.pegasus.subsystems.swerve;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.utils.Dataset;
import edu.greenblitz.pegasus.utils.GBMath;
import edu.greenblitz.pegasus.utils.PIDObject;
import edu.greenblitz.pegasus.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.General.Motors.NEO_PHYSICAL_TICKS_TO_RADIANS;

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
		angleMotor.config(RobotMap.Pegasus.Swerve.KazaSwerve.baseAngConfObj);

		linearMotor = new GBSparkMax(linearMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
		linearMotor.config(RobotMap.Pegasus.Swerve.KazaSwerve.baseLinConfObj.withInverted(linInverted));

		lamprey = new AnalogInput(lampreyID);
		lamprey.setAverageBits(2);
		this.feedforward = new SimpleMotorFeedforward(RobotMap.Pegasus.Swerve.ks, RobotMap.Pegasus.Swerve.kv, RobotMap.Pegasus.Swerve.ka);;
	}

	
	public KazaSwerveModule(KazaSwerveModuleConfigObject KazaModuleConfigObject){
		this(KazaModuleConfigObject.angleMotorID,
		KazaModuleConfigObject.linearMotorID,
		KazaModuleConfigObject.AbsoluteEncoderID,
		KazaModuleConfigObject.linInverted);
	}

	public static class KazaSwerveModuleConfigObject{
		private int angleMotorID;
		private int linearMotorID;
		private int AbsoluteEncoderID;
		private boolean linInverted;
		
		

		public KazaSwerveModuleConfigObject(int angleMotorID, int linearMotorID, int AbsoluteEncoderID, boolean linInverted){
			this.angleMotorID = angleMotorID;
			this.linearMotorID = linearMotorID;
			this.AbsoluteEncoderID = AbsoluteEncoderID;
			this.linInverted = linInverted;
		}
	}
	


	/** sets to module to be at the given module state */
	@Override
	public void setModuleState(SwerveModuleState moduleState) {
		setLinSpeed(moduleState.speedMetersPerSecond);
		rotateToAngle(moduleState.angle.getRadians());
	}
	
	/** gets a target angle in radians, sets the internal PIDController to the shortest route to the angle
	 * relative to the encoder module angle*/
	@Override
	public void rotateToAngle(double angle) {

		double diff = GBMath.modulo(angle - getModuleAngle(), 2 * Math.PI);
		diff -= diff > Math.PI ? 2*Math.PI : 0;
		angle = getModuleAngle() + diff;

		angleMotor.getPIDController().setReference(angle, ControlType.kPosition);

		targetAngle = angle;
	}


	/** get the module angle by radians */
	@Override
	public double getModuleAngle() {
		return angleMotor.getEncoder().getPosition();
	}

	@Override
	public double getCurrentVelocity() {
		return (linearMotor.getEncoder().getVelocity());
	}



	/** resetEncoderToValue - reset the angular encoder to RADIANS */
	@Override
	public void resetEncoderToValue(double angle) {
		angleMotor.getEncoder().setPosition(angle);
	}
	
	@Override
	public void resetEncoderToValue(){
		angleMotor.getEncoder().setPosition(0);
	}


	@Override
	public void resetEncoderByAbsoluteEncoder(SwerveChassis.Module module){
		resetEncoderToValue(Calibration.CALIBRATION_DATASETS.get(module).linearlyInterpolate(getAbsoluteEncoderValue())[0] * NEO_PHYSICAL_TICKS_TO_RADIANS);
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
		linearMotor.getPIDController().setReference(speed,ControlType.kVelocity, 0, feedforward.calculate(speed));
	}

	@Override
	public void stop(){
		angleMotor.set(0);
		linearMotor.set(0);
	}

	//only for debugging

	@Override
	public double getTargetAngle() {
		return targetAngle;
	}

	@Override
	public double getTargetVel() {
		return targetVel;
	}

	@Override
	public SwerveModuleState getModuleState(){
		return new SwerveModuleState(getCurrentVelocity(),new Rotation2d(this.getModuleAngle()));
	}

	/** get the lamprey's angle raw voltage*/
	@Override
	public double getAbsoluteEncoderValue(){
		return lamprey.getVoltage();
	}
	@Override
	public void setRotPowerOnlyForCalibrations(double power){
		angleMotor.set(power);
	}
	@Override
	public void setLinPowerOnlyForCalibrations(double power){
		linearMotor.set(power);
	}


}