package edu.greenblitz.tobyDetermined.subsystems.swerve;

import edu.greenblitz.tobyDetermined.Field;
import edu.greenblitz.tobyDetermined.Robot;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.Limelight;
import edu.greenblitz.utils.PigeonGyro;
import edu.wpi.first.math.MatBuilder;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.filter.MedianFilter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.*;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.photonvision.EstimatedRobotPose;

public class SwerveChassis extends GBSubsystem {

	private static SwerveChassis instance;
	private final SwerveModule frontRight, frontLeft, backRight, backLeft;
	private final PigeonGyro pigeonGyro;
	private final SwerveDriveKinematics kinematics;
	private final SwerveDrivePoseEstimator poseEstimator;
	private final Field2d field = new Field2d();

	private final Ultrasonic ultrasonic;
    private final int FILTER_BUFFER_SIZE = 15;

	public static final double TRANSLATION_TOLERANCE = 0.05;
	public static final double ROTATION_TOLERANCE = 4;

	public SwerveChassis() {
		this.frontLeft = new KazaSwerveModule(RobotMap.Swerve.KazaModuleFrontLeft);
		this.frontRight = new KazaSwerveModule(RobotMap.Swerve.KazaModuleFrontRight);
		this.backLeft = new KazaSwerveModule(RobotMap.Swerve.KazaModuleBackLeft);
		this.backRight = new KazaSwerveModule(RobotMap.Swerve.KazaModuleBackRight);
		this.ultrasonic = new Ultrasonic(RobotMap.Ultrasonic.PING_CHANNEL, RobotMap.Ultrasonic.ECHO_CHANNEL);
		Ultrasonic.setAutomaticMode(true);

		
		this.pigeonGyro = new PigeonGyro(RobotMap.gyro.pigeonID);
		
		this.kinematics = new SwerveDriveKinematics(
				RobotMap.Swerve.SwerveLocationsInSwerveKinematicsCoordinates
		);
		this.poseEstimator = new SwerveDrivePoseEstimator(this.kinematics,
				getPigeonAngle(),
				getSwerveModulePositions(),
				new Pose2d(new Translation2d(), new Rotation2d()),//Limelight.getInstance().estimateLocationByVision(),
				new MatBuilder<>(Nat.N3(), Nat.N1()).fill(RobotMap.Vision.STANDARD_DEVIATION_ODOMETRY, RobotMap.Vision.STANDARD_DEVIATION_ODOMETRY, RobotMap.Vision.STANDARD_DEVIATION_ODOMETRY),
				new MatBuilder<>(Nat.N3(), Nat.N1()).fill(RobotMap.Vision.STANDARD_DEVIATION_VISION2D, RobotMap.Vision.STANDARD_DEVIATION_VISION2D, RobotMap.Vision.STANDARD_DEVIATION_VISION_ANGLE));
		SmartDashboard.putData("field", getField());
		field.getObject("apriltag").setPose(Field.Apriltags.redApriltagLocationId1.toPose2d());
	}


	public static SwerveChassis getInstance() {
		if (instance == null) {
			init();
			SmartDashboard.putBoolean("chassis initialized via getinstance", true);
		}
		return instance;
	}

	public static void init(){
		instance = new SwerveChassis();
	}
	
	@Override
	public void periodic() {
		updatePoseEstimation();
		field.setRobotPose(getRobotPose());
	}
	
	public void resetAll(Pose2d pose){
		poseEstimator.resetPosition(getPigeonAngle(), getSwerveModulePositions(), pose);
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
	public void resetEncodersByCalibrationRod() {
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
	 * get the absolute encoder value of a specific module
	 */
	public double getModuleAbsoluteEncoderValue(Module module) {
		return getModule(module).getAbsoluteEncoderValue();
	}
	
	
	/**
	 * all code below is self-explanatory - well, after a long time It's maybe not self-explanatory
	 * <p>
	 * ALL IN RADIANS, NOT DEGREES
	 */
	
	public double getModuleAngle(Module module) {
		return getModule(module).getModuleAngle();
	}
	
	public boolean moduleIsAtAngle(Module module,double targetAngleInRads, double errorInRads){
		return getModule(module).isAtAngle(targetAngleInRads,errorInRads);
	}
	
	public void resetChassisPose() {
		pigeonGyro.setYaw(0);
		poseEstimator.resetPosition(getPigeonAngle(), getSwerveModulePositions(), new Pose2d());
	}
	
	/**
	 * returns chassis angle in radians
	 */
	private Rotation2d getPigeonAngle() {
		return new Rotation2d(pigeonGyro.getYaw());
	}
	
	public double getChassisAngle() {
		return getRobotPose().getRotation().getRadians();
		
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
	
	public void moveByChassisSpeeds(double forwardSpeed, double leftwardSpeed, double angSpeed, double currentAng) {
		ChassisSpeeds chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
				forwardSpeed,
				leftwardSpeed,
				angSpeed,
				Rotation2d.fromDegrees(Math.toDegrees(currentAng)));
		SwerveModuleState[] states = kinematics.toSwerveModuleStates(chassisSpeeds);
		SwerveModuleState[] desaturatedStates = desaturateSwerveModuleStates(states);
		setModuleStates(desaturatedStates);
	}

	/**
	 * makes sure no module is requested to move faster than possible by linearly scaling all module velocities to comply with the constraint
	 * @param states original velocity states computed from the kinematics
	 * @return states that create the same ratio between speeds but scaled down
	 */
	private static SwerveModuleState[] desaturateSwerveModuleStates(SwerveModuleState[] states){
		double desaturationFactor = 1;
		for (SwerveModuleState state : states){
			desaturationFactor = Math.max(desaturationFactor, state.speedMetersPerSecond / RobotMap.Swerve.MAX_VELOCITY);
		}
		SwerveModuleState[] desaturatedStates = new SwerveModuleState[states.length];
		for (int i = 0; i < states.length; i++){
			desaturatedStates[i] = new SwerveModuleState(states[i].speedMetersPerSecond / desaturationFactor, states[i].angle);
		}
		return desaturatedStates;
	}
	
	public ChassisSpeeds getChassisSpeeds() {
		return kinematics.toChassisSpeeds(getModuleState(Module.FRONT_LEFT),
				getModuleState(Module.FRONT_RIGHT),
				getModuleState(Module.BACK_LEFT),
				getModuleState(Module.BACK_RIGHT));
	}
	
	public SwerveModulePosition[] getSwerveModulePositions() {
		return new SwerveModulePosition[]{
				frontLeft.getCurrentPosition(),
				frontRight.getCurrentPosition(),
				backLeft.getCurrentPosition(),
				backRight.getCurrentPosition()
		};
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
			Translation2d translationFromCenter = RobotMap.Swerve.SwerveLocationsInSwerveKinematicsCoordinates[i];
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
	
	public void updatePoseEstimation() {
		poseEstimator.update(getPigeonAngle(), getSwerveModulePositions());
		Limelight.getInstance().getUpdatedPoseEstimator().ifPresent((EstimatedRobotPose pose) -> poseEstimator.addVisionMeasurement(pose.estimatedPose.toPose2d(), pose.timestampSeconds));
	}



	public Pose2d getRobotPose() {
		return poseEstimator.getEstimatedPosition();
	}

	public boolean isAtPose(Pose2d goalPose){
		Pose2d robotPose = getRobotPose();

		boolean isAtX = Math.abs(goalPose.getX() - robotPose.getX()) <= TRANSLATION_TOLERANCE;
		boolean isAtY = Math.abs(goalPose.getY() - robotPose.getY()) <= TRANSLATION_TOLERANCE;

		Rotation2d angDifference = (goalPose.getRotation().minus(robotPose.getRotation()));
		boolean isAtAngle = angDifference.getRadians() <= ROTATION_TOLERANCE
				|| (Math.PI*2) - angDifference.getRadians() <= ROTATION_TOLERANCE;

		return isAtAngle && isAtX && isAtY;
	}
	
	public Sendable getField() {
		return field;
	}
	
	public SwerveModuleState getModuleState(Module module) {
		return getModule(module).getModuleState();
	}
	
	public enum Module {
		FRONT_LEFT,
		FRONT_RIGHT,
		BACK_LEFT,
		BACK_RIGHT
	}

    public boolean moduleIsAtAngle(Module module, double errorInRads) {
        return getModule(module).isAtAngle(errorInRads);
    }

    public void resetChassisPose(Pose2d pose) {
        poseEstimator.resetPosition(getPigeonAngle(), getSwerveModulePositions(), pose);
    }

    public void moveByChassisSpeeds(ChassisSpeeds chassisSpeeds) {
        moveByChassisSpeeds(chassisSpeeds.vxMetersPerSecond,
                chassisSpeeds.vyMetersPerSecond,
                chassisSpeeds.omegaRadiansPerSecond,
                getChassisAngle()
        );
        SmartDashboard.putNumber("omega", chassisSpeeds.omegaRadiansPerSecond);
    }

    public double getUltrasonicDistance(){
        MedianFilter filter = new MedianFilter(FILTER_BUFFER_SIZE);
        return filter.calculate(ultrasonic.getRangeMM());
    }
    /**
     * set the idle mode of the linear motor to brake
     */
    public void setIdleModeBrake() {
        for (Module module : Module.values()) {
            getModule(module).setLinIdleModeBrake();
        }
    }

	public void setIdleModeCoast(){
		for (Module module : Module.values()){
			getModule(module).setLinIdleModeCoast();
		}
	}

}