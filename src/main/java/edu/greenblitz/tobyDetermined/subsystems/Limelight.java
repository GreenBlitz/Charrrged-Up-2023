package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.wpilibj.Timer;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.RobotPoseEstimator;

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
			instance = new Limelight();
		}
		return instance;
	}
	
	
	public double getYawTarget() {
		var result = camera.getLatestResult();
		if (!result.hasTargets()) {
			return 0;
		}
		PhotonTrackedTarget target = result.getBestTarget();
		return Math.IEEEremainder(Math.toRadians(target.getYaw()), 2 * Math.PI);
	}
	
	/**
	 * it is minus because photon vision is inverted
	 *
	 * @return the curr angle minus the target angle
	 */
	public double fieldRelativeTargetYaw() {
		return Math.IEEEremainder(SwerveChassis.getInstance().getChassisAngle() - getYawTarget(), 2 * Math.PI);
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
		if (FindTarget()) {
			Transform3d target = camera.getLatestResult().getBestTarget().getBestCameraToTarget().inverse();
			Pose3d camPose = RobotMap.Vision.apriltagLocation.transformBy(target);
			cameraToRobot = new Transform2d(RobotMap.Vision.initialCamPosition.getTranslation().toTranslation2d(),RobotMap.Vision.initialCamPosition.getRotation().toRotation2d());
			Pose2d robotPose = camPose.toPose2d().transformBy(cameraToRobot);
			return robotPose;
		}
		Pose2d robotPose = new Pose2d(new Translation2d(), new Rotation2d());
		return robotPose;
	}

	public Pair<Pose2d, Double> visionPoseEstimator() {
		var camList = new ArrayList<Pair<PhotonCamera, Transform3d>>();
		camList.add(new Pair<PhotonCamera, Transform3d>(camera, RobotMap.Vision.initialCamPosition));
		poseEstimator = new RobotPoseEstimator(RobotMap.Vision.aprilTagFieldLayout, RobotPoseEstimator.PoseStrategy.LOWEST_AMBIGUITY, camList);

		double currentTime = Timer.getFPGATimestamp();
		Optional<Pair<Pose3d, Double>> result = poseEstimator.update();
		if (result.isPresent()) {
			return new Pair<Pose2d, Double>(result.get().getFirst().toPose2d(), currentTime - result.get().getSecond());
		} else {
			return new Pair<Pose2d, Double>(null, 0.0);
		}
	}

	public Pair<Pose3d, Double> updateVision(){
		return poseEstimator.update().get();
	}

	
	public boolean FindTarget() {
		return camera.getLatestResult().hasTargets();
	}
	
	/**
	 * @return the apriltag id
	 */
	public int FindTagId() {
		if (FindTarget()){return camera.getLatestResult().getBestTarget().getFiducialId();}
		else {return 0;}
	}
	
}





