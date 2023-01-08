package edu.greenblitz.pegasus.subsystems.swerve;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.Limelight;
import edu.greenblitz.pegasus.utils.PigeonGyro;
import edu.greenblitz.pegasus.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.utils.GBMath;
import edu.wpi.first.math.MatBuilder;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.*;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.greenblitz.pegasus.utils.PigeonGyro;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveChassis extends GBSubsystem {
	
	private final SwerveModule frontRight, frontLeft, backRight, backLeft;
	private final PigeonGyro pigeonGyro;
	private final SwerveDriveKinematics kinematics;
	private final SwerveDrivePoseEstimator poseEstimator;
	private final Field2d field = new Field2d();

	
	public enum Module {
		FRONT_LEFT,
		FRONT_RIGHT,
		BACK_LEFT,
		BACK_RIGHT
	}
	public SwerveChassis() {
		this.frontLeft = new KazaSwerveModule(RobotMap.Pegasus.Swerve.KazaModule1);
		this.frontRight = new KazaSwerveModule(RobotMap.Pegasus.Swerve.KazaModule2);
		this.backLeft = new KazaSwerveModule(RobotMap.Pegasus.Swerve.KazaModule3);
		this.backRight = new KazaSwerveModule(RobotMap.Pegasus.Swerve.KazaModule4);

		this.pigeonGyro = new PigeonGyro(RobotMap.Pegasus.gyro.pigeonID);
		
		this.kinematics = new SwerveDriveKinematics(
				RobotMap.Pegasus.Swerve.SwerveLocationsInSwerveKinematicsCoordinates
		);
		this.poseEstimator = new SwerveDrivePoseEstimator(this.getPigeonAngle(), Limelight.getInstance().estimateLocationByVision(), this.kinematics,
				new MatBuilder<>(Nat.N3(), Nat.N1()).fill(0.02, 0.02, 0.01),
				new MatBuilder<>(Nat.N1(), Nat.N1()).fill(0.1),//0.02),
				new MatBuilder<>(Nat.N3(), Nat.N1()).fill(0.1,0.1,0.01));//0.1, 0.1, 0.01));
		SmartDashboard.putData("field",getField());
		field.getObject("apriltag").setPose(RobotMap.Pegasus.Vision.apriltagLocation.toPose2d());
	}
	
	
	private static SwerveChassis instance;
	
	public static SwerveChassis getInstance() {
		if (instance == null) {
			instance = new SwerveChassis();
		}
		return instance;
	}
	
	
	@Override
	public void periodic(){
		updatePoseEstimation();
		field.setRobotPose(getRobotPose());
	}
	
	/**
	 * @return returns the swerve module based on its name
	 */
	private SwerveModule getModule(Module module) {
		switch (module) {
			case BACK_LEFT:
				return backLeft;
			case BACK_RIGHT:
				return backRight;
			case FRONT_LEFT:
				return frontLeft;
			case FRONT_RIGHT:
				return frontRight;
		}
		return null;
	}
	
	/**
	 * stops all the modules (power(0))
	 */
	public void stop() {
		frontRight.stop();
		frontLeft.stop();
		backRight.stop();
		backLeft.stop();
	}
	
	/**
	 * resetting all the angle motor's encoders to 0
	 */
	public void resetEncodersByCalibrationRod(){
		getModule(Module.FRONT_LEFT).resetEncoderToValue(0);
		getModule(Module.FRONT_RIGHT).resetEncoderToValue(0);
		getModule(Module.BACK_LEFT).resetEncoderToValue(0);
		getModule(Module.BACK_RIGHT).resetEncoderToValue(0);
	}

	public void resetAllEncoders() {
		getModule(Module.FRONT_LEFT).resetEncoderByAbsoluteEncoder(Module.FRONT_LEFT);
		getModule(Module.FRONT_RIGHT).resetEncoderByAbsoluteEncoder(Module.FRONT_RIGHT);
		getModule(Module.BACK_LEFT).resetEncoderByAbsoluteEncoder(Module.BACK_LEFT);
		getModule(Module.BACK_RIGHT).resetEncoderByAbsoluteEncoder(Module.BACK_RIGHT);

	}




	/**
	 * all code below is self-explanatory - well, after a long time It's maybe not self-explanatory
	 * <p>
	 * ALL IN RADIANS, NOT DEGREES
	 */

	/**
	 * get the absolute encoder value of a specific module
	 */
	public double getModuleAbsoluteValue(Module module) {
		return getModule(module).getAbsoluteEncoderValue();
	}

	public double getModuleAngle(Module module) {
		return getModule(module).getModuleAngle();
	}
	
	/**
	 * make the pigeon (gyro) set this angle to be the angle in radians
	 */
	public void resetChassisAngle(double angInRads) {
		
		pigeonGyro.setYaw(angInRads);
	}
	
	/**
	 * when no parameter is given reset the chassis angle to 0
	 */
	
	public void resetChassisAngle() {
		pigeonGyro.setYaw(0);
		poseEstimator.resetPosition(new Pose2d(), getPigeonAngle());
	}


	/** returns chassis angle in radians */
	private Rotation2d getPigeonAngle() {
		return new Rotation2d(pigeonGyro.getYaw());
	}

	public double getChassisAngle(){
		return getRobotPose().getRotation().getRadians();

	}
	
	/**
	 * get module target angle (radians)
	 */
	public double getTarget(Module module) {//todo make more informative name
		return getModule(module).getTargetAngle();
		
	}
	
	/**
	 * setting module states to all 4 modules
	 */
	public void setModuleStates(SwerveModuleState[] states) {
		setModuleStateForModule(Module.FRONT_LEFT,
				SwerveModuleState.optimize(states[0], new Rotation2d(getModuleAngle(Module.FRONT_LEFT))));
		setModuleStateForModule(Module.FRONT_RIGHT,
				SwerveModuleState.optimize(states[1], new Rotation2d(getModuleAngle(Module.FRONT_RIGHT))));
		setModuleStateForModule(Module.BACK_LEFT,
				SwerveModuleState.optimize(states[2], new Rotation2d(getModuleAngle(Module.BACK_LEFT))));
		setModuleStateForModule(Module.BACK_RIGHT,
				SwerveModuleState.optimize(states[3], new Rotation2d(getModuleAngle(Module.BACK_RIGHT))));
		
		
	}
	
	public ChassisSpeeds getCurSpeed(){
		return kinematics.toChassisSpeeds(frontLeft.getModuleState(),frontRight.getModuleState(),backLeft.getModuleState(),backRight.getModuleState());
	}
	
	public void moveByChassisSpeeds(double forwardSpeed, double leftwardSpeed, double angSpeed, double currentAng) {
		ChassisSpeeds chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
				forwardSpeed,
				leftwardSpeed,
				angSpeed,
				Rotation2d.fromDegrees(Math.toDegrees(currentAng)));
		SwerveModuleState[] states = kinematics.toSwerveModuleStates(chassisSpeeds);
		setModuleStates(states);
	}
	
	public ChassisSpeeds getChassisSpeeds(){
		return kinematics.toChassisSpeeds(getModuleState(Module.FRONT_LEFT),
				getModuleState(Module.FRONT_RIGHT),
				getModuleState(Module.BACK_LEFT),
				getModuleState(Module.BACK_RIGHT));
	}
	
	public SwerveDriveKinematics getKinematics() {
		return this.kinematics;
	}

	public PigeonGyro getPigeonGyro() {
		return pigeonGyro;
	}
	
	/**
	 * moving a single module by module state
	 */
	private void setModuleStateForModule(Module module, SwerveModuleState state) {
		getModule(module).setModuleState(state);
	}

	
	/**
	 * rotates chassis by percentOutput [-1, 1]
	 */
	public void rotateChassisByPower(double percentOutput) {
		for (int i = 0; i < Module.values().length; i++) {
			Translation2d translationFromCenter = RobotMap.Pegasus.Swerve.SwerveLocationsInSwerveKinematicsCoordinates[i];
			getModule(Module.values()[i]).rotateToAngle(Math.atan2(translationFromCenter.getY(), translationFromCenter.getX()) + Math.PI * 0.5);
			getModule(Module.values()[i]).setLinPowerOnlyForCalibrations(percentOutput);
		}
	}
	
	
	/**
	 * for calibration purposes
	 */
	public void rotateModuleByPower(Module module, double power) {
		getModule(module).setRotPowerOnlyForCalibrations(power);
	}
	public void updatePoseEstimation(){ //todo NaN protection
		poseEstimator.update(getPigeonAngle(),
				frontLeft.getModuleState(), frontRight.getModuleState(),
				backLeft.getModuleState(), backRight.getModuleState());
		if(Limelight.getInstance().FindTarget()){poseEstimator.addVisionMeasurement(Limelight.getInstance().estimateLocationByVision(),Limelight.getInstance().getTimeStamp());}
	}

	public Pose2d getRobotPose(){return poseEstimator.getEstimatedPosition();}
	
	public Sendable getField(){return field;}

	public SwerveModuleState getModuleState (Module module){
	return getModule(module).getModuleState();
	}
	
}
