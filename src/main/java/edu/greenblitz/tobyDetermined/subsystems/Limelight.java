package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.photonvision.PhotonCamera;
import org.photonvision.RobotPoseEstimator;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.util.ArrayList;
import java.util.Optional;


public class Limelight extends GBSubsystem {
	private static Limelight instance;
	private PhotonCamera camera;
	private RobotPoseEstimator poseEstimator;
	private Transform2d cameraToRobot;
	
	private Limelight() {
		camera = new PhotonCamera("photonvision");
	}
	
	public static Limelight getInstance() {
		if (instance == null) {
			init();
			SmartDashboard.putBoolean("limelight initialized via getinstance", true);
		}
		return instance;
	}

	public static void init(){
		instance = new Limelight();
	}
	
	
	
	/**
	 * @return an array of three dimensions [x - forward , y - left, z - up]
	 */
	public Transform3d targetPos() {
		var result = camera.getLatestResult();
		if (!result.hasTargets()) {
			return new Transform3d();
		}
		Transform3d target = result.getBestTarget().getBestCameraToTarget();
		return target;
	}
	
	/**
	 * @return the estimated time the frame was taken
	 */
	public double getTimeStamp() {
		var result = camera.getLatestResult();
		return result.getTimestampSeconds();
	}
	
	/**
	 * transforms the target location and the initial cam location.
	 *
	 * @return the location of the vision by the robot.
	 */
	//todo improve default output
	public Pose2d estimateLocationByVision() {
		if (hasTarget()) {
			Transform3d target = camera.getLatestResult().getBestTarget().getBestCameraToTarget().inverse();
			Pose3d camPose = RobotMap.Vision.apriltagLocation.transformBy(target);
			cameraToRobot = new Transform2d(RobotMap.Vision.initialCamPosition.getTranslation().toTranslation2d(), RobotMap.Vision.initialCamPosition.getRotation().toRotation2d());
			Pose2d robotPose = camPose.toPose2d().transformBy(cameraToRobot);
			return robotPose;
		}
		Pose2d robotPose = new Pose2d(new Translation2d(), new Rotation2d());
		return robotPose;
	}
	
	public Pair<Pose2d, Double> visionPoseEstimator() {
		ArrayList<Pair<PhotonCamera, Transform3d>> camList = new ArrayList<>();
		camList.add(new Pair<>(camera, RobotMap.Vision.initialCamPosition));
		poseEstimator = new RobotPoseEstimator(RobotMap.Vision.aprilTagFieldLayout, RobotPoseEstimator.PoseStrategy.LOWEST_AMBIGUITY, camList);
		
		double currentTime = Timer.getFPGATimestamp();
		Optional<Pair<Pose3d, Double>> visionPose = poseEstimator.update();
		if (visionPose.isPresent()) {
			return new Pair<>(visionPose.get().getFirst().toPose2d(), currentTime - visionPose.get().getSecond());
		} else {
			return new Pair<>(null, 0.0);
		}
	}
	
	public boolean hasTarget() {
		return camera.getLatestResult().hasTargets();
	}
	
	/**
	 * @return the apriltag id
	 */
	public int findTagId() {
		if (hasTarget()) {
			return camera.getLatestResult().getBestTarget().getFiducialId();
		} else {
			return 0;
		}
	}
	
}





