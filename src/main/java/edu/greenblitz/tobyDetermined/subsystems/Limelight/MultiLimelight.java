package edu.greenblitz.tobyDetermined.subsystems.Limelight;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MultiLimelight extends GBSubsystem {

	private static MultiLimelight instance;
	private List<Limelight> limelights;


	private MultiLimelight(){
		limelights = new ArrayList<>();
		for(String limelightName : RobotMap.Vision.LIMELIGHT_NAMES){
			limelights.add(new Limelight(limelightName));
		}
	}

	public static MultiLimelight getInstance() {
		if (instance == null) {
			init();
			SmartDashboard.putBoolean("limelight initialized via getinstance", true);
		}
		return instance;
	}

	public static void init(){
		instance = new MultiLimelight();
	}

	public List<Optional<Pair<Pose2d, Double>>> getAllEstimates(){
		ArrayList<Optional<Pair<Pose2d, Double>>> estimates = new ArrayList<>();
		for (Limelight limelight : limelights){
			estimates.add(limelight.getUpdatedPoseEstimation());
		}
		return estimates;
	}
	
	public boolean isConnected(){
		return limelights.get(0).hasTarget();
	}

	public Optional<Pair<Pose2d, Double>> getFirstAvailableTarget(){
		for(Optional<Pair<Pose2d, Double>> output : getAllEstimates()){
			if (output.isPresent()){
				return output;
			}
		}
		return Optional.empty();
	}
}
