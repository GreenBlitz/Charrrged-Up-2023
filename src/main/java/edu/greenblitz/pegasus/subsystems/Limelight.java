package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.utils.GBMath;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.wpilibj.Timer;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;


public class Limelight extends GBSubsystem {
	private static Limelight instance;
	private PhotonCamera camera;
	private Transform2d cameraToRobot;
	private Limelight(){
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
		if (!result.hasTargets()){return 0;}
		PhotonTrackedTarget target = result.getBestTarget();
		return GBMath.modulo(Math.toRadians(target.getYaw()),2 * Math.PI);
	}
	
	/**
	 * it is minus because photon vision is inverted
	 * @return the curr angle minus the target angle
	 */
	public double fieldRelativeTargetYaw(){
		return GBMath.modulo(SwerveChassis.getInstance().getChassisAngle() - getYawTarget(),2 * Math.PI);
	}
	
	/**
	 *
	 * @return an array of three dimensions [x - forward , y - left, z - up]
	 */
	public Transform3d targetPos(){
		var result = camera.getLatestResult();
		if (!result.hasTargets()){return new Transform3d();}
		Transform3d target = result.getBestTarget().getBestCameraToTarget();
		return target;
	}

	/**
	 *
	 * @return the estimated time the frame was taken
	 */
	public double getTimeStamp(){
		var result = camera.getLatestResult();
		return result.getTimestampSeconds();
	}

	/**
	 * transforms the target location and the initial cam location.
	 * @return the location of the vision by the robot.
	 */
	public Pose2d estimateLocationByVision(){
		if(FindTarget()){
			Transform3d target = camera.getLatestResult().getBestTarget().getBestCameraToTarget().inverse();
			Pose3d camPose = RobotMap.Pegasus.Vision.apriltagLocation.transformBy(target);
			cameraToRobot = RobotMap.Pegasus.Vision.initialCamPosition;
			Pose2d robotPose = camPose.toPose2d().transformBy(cameraToRobot);
			return robotPose;
		}
		Pose2d robotPose = new Pose2d(new Translation2d(),new Rotation2d());
		return robotPose;
	}
	
	public boolean FindTarget(){
		return camera.getLatestResult().hasTargets();
	}
	
	/**
	 *
	 * @return the apriltag id
	 */
	public int FindTagId(){return camera.getLatestResult().getBestTarget().getFiducialId();}

}





