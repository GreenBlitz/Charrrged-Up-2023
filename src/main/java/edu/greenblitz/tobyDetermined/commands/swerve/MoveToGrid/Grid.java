package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;

import static com.google.common.collect.Iterables.indexOf;

public class Grid {
    private static Grid instance;
    private int selectedPos;
    private int selectedHeight;

    public Grid() {
        this.selectedPos = 0;
    }

    public static Grid getInstance() {
        if (instance == null) {
            instance = new Grid();
        }
        return instance;
    }

    public Pose2d getLocation() {
        return Location[selectedPos];
    }

    public int getSelectedPos(){
        return selectedPos;
    }

    public void setPoseSelectedPose(Pose2d pose) {
        for (int i = 0; i<Location.length; i++){
			if(Location[i].equals(pose)) selectedPos = i;
		}
    }

	public void setSelectedPos(int pose){
		this.selectedPos = pose;
	}

    public Pose2d[] Location = {
            new Pose2d(new Translation2d(0, 0), new Rotation2d()),
            new Pose2d(new Translation2d(0, 0), new Rotation2d()),
            new Pose2d(new Translation2d(0, 1), new Rotation2d()),
            new Pose2d(new Translation2d(1, 1), new Rotation2d()),
            new Pose2d(new Translation2d(0.5, 0), new Rotation2d()),
            new Pose2d(new Translation2d(0, 0.5), new Rotation2d()),
            new Pose2d(new Translation2d(0.5, 0.5), new Rotation2d()),
            new Pose2d(new Translation2d(1, 0.5), new Rotation2d()),
            new Pose2d(new Translation2d(0.5, 1), new Rotation2d())
    };

    /**
     * allows you to get a pose from the Location by its index
     * fitted to the alliance
     */
    public Pose2d getPose2D(DriverStation.Alliance allianceColor) {
        if (allianceColor == DriverStation.Alliance.Blue) {
            return Location[selectedPos];
        } else {
            return new Pose2d(new Translation2d(RobotMap.General.Field.fieldLength - Location[selectedPos].getX(), Location[selectedPos].getY()), new Rotation2d(0));
        }
    }

    public enum Height{
        HIGH,
        MEDIUM,
        LOW,
    }
    public int getSelectedHeight(){
        return selectedHeight;
    }

    public Height getHeight(){
        return Height.values()[selectedHeight];
    }

    public void setSelectedHeight(int height){
        selectedHeight = height;
    }
}