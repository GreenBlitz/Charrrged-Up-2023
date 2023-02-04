package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;

public class Grid {
	private static Grid instance;
	private Location selectedPos;
	public Grid() {
		this.selectedPos = Location.POS1;
	}
	
	public static Grid getInstance() {
		if (instance == null) {
			instance = new Grid();
		}
		return instance;
	}
	
	public Location getPose(){
		return selectedPos;
	}
	
	public void setPose(Location pose){
		 selectedPos = pose;
	}
	

	public enum Location {
		
		POS1(new Pose2d()),
		POS2(new Pose2d()),
		POS3(new Pose2d());
		private final Pose2d pose;
		private Location(Pose2d pose){
			this.pose = pose;
		}
		
		public Pose2d getPose(DriverStation.Alliance allianceColor) {
			if(allianceColor == DriverStation.Alliance.Blue){
				return pose;
			}
			else {
				return new Pose2d(new Translation2d(RobotMap.General.Field.fieldLength - pose.getX(),pose.getY()), new Rotation2d(0));
			}
		}
		
		
		
	}
}
