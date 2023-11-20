package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.Field;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;

import java.util.Optional;


public class Photonvision extends GBSubsystem {
	private static Photonvision instance;
	private PhotonCamera camera;
	private PhotonPoseEstimator poseEstimator;
	
	public Photonvision() {
		camera = new PhotonCamera("photonvision");
		poseEstimator = new PhotonPoseEstimator(
				Field.Apriltags.aprilTagFieldLayout,
				PhotonPoseEstimator.PoseStrategy.AVERAGE_BEST_TARGETS,
				camera,
				RobotMap.Vision.ROBOT_TO_CAMERA
		);
	}
	
	public static Photonvision getInstance() {
		init();
		return instance;
	}

	public static void init(){
		instance = new Photonvision();
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
	public Optional<EstimatedRobotPose> getUpdatedPoseEstimator() {
		return poseEstimator.update();
	}
	
	public boolean hasTarget() {
		return getUpdatedPoseEstimator().isPresent();
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





