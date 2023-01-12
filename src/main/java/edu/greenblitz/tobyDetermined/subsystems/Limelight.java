package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.geometry.*;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;


public class Limelight extends GBSubsystem {
	private static Limelight instance;
	private PhotonCamera camera;
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
			cameraToRobot = RobotMap.Vision.initialCamPosition;
			Pose2d robotPose = camPose.toPose2d().transformBy(cameraToRobot);
			return robotPose;
		}
		Pose2d robotPose = new Pose2d(new Translation2d(), new Rotation2d());
		return robotPose;
	}
	
	public boolean FindTarget() {
		return camera.getLatestResult().hasTargets();
	}
	
	/**
	 * @return the apriltag id
	 */
	public int FindTagId() {
		return camera.getLatestResult().getBestTarget().getFiducialId();
	}
	
}





