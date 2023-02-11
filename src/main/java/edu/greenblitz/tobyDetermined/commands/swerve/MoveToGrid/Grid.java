package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;

public class Grid {
    private static Grid instance;
    private int selectedPositionID;
    private int selectedHeightID;

    private Grid() {
        this.selectedPositionID = 0;
        this.selectedHeightID = 0;
    }

    public static Grid getInstance() {
        if (instance == null) {
            instance = new Grid();
        }
        return instance;
    }
    
    /**
     * allows you to get a pose from the Location by its index
     * fitted to the alliance
     */
    public Pose2d getSelectedPosition(DriverStation.Alliance allianceColor) {
        if (allianceColor == DriverStation.Alliance.Blue) {
            return Location[selectedPositionID];
        } else {
            return new Pose2d(
                RobotMap.General.Field.fieldLength - Location[selectedPositionID].getX(),
                Location[selectedPositionID].getY(),
                new Rotation2d(0));
        }
    }
    
    public int getSelectedPositionID(){
        return selectedPositionID;
    }
    
    public Height getSelectedHeight(){
        return Height.values()[selectedHeightID];
    }
    
    public int getSelectedHeightID(){
        return selectedHeightID;
    }
    
    public void setSelectedPosition(int positionID){
        this.selectedPositionID = positionID;
    }
    
    public void setSelectedHeight(int heightID){
        this.selectedHeightID = heightID;
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

    public enum Height{
        HIGH,
        MEDIUM,
        LOW,
    }
}