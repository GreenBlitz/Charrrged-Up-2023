package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Grid {
	private static Grid instance;
	private Location selectedPos;
	public Grid() {
		this.selectedPos = Location.POS0;
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
		POS0(new Pose2d(new Translation2d(0,0),new Rotation2d())),
		POS1(new Pose2d(new Translation2d(1,0),new Rotation2d())),
		POS2(new Pose2d(new Translation2d(0,1),new Rotation2d())),
		POS3(new Pose2d(new Translation2d(1,1),new Rotation2d())),
		POS4(new Pose2d(new Translation2d(0.5,0),new Rotation2d())),
		POS5(new Pose2d(new Translation2d(0,0.5),new Rotation2d())),
		POS6(new Pose2d(new Translation2d(0.5,0.5),new Rotation2d())),
		POS7(new Pose2d(new Translation2d(1,0.5),new Rotation2d())),
		POS8(new Pose2d(new Translation2d(0.5,1),new Rotation2d()));
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
