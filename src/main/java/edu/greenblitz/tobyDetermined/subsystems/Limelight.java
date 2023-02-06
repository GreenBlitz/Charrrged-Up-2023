package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.geometry.*;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonTrackedTarget;

import java.util.Optional;


public class Limelight extends GBSubsystem {
	private static Limelight instance;
	private PhotonCamera camera;
	private PhotonPoseEstimator poseEstimator;
	
	private Limelight() {
		camera = new PhotonCamera("photonvision");
		poseEstimator = new PhotonPoseEstimator(
				RobotMap.Vision.aprilTagFieldLayout,
				PhotonPoseEstimator.PoseStrategy.AVERAGE_BEST_TARGETS,
				camera,
				RobotMap.Vision.RobotToCamera
		);
	}
	
	public static Limelight getInstance() {
		if (instance == null) {
			instance = new Limelight();
		}
		return instance;
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
	
	public Optional<EstimatedRobotPose> visionPoseEstimator() {
		return poseEstimator.update();
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





