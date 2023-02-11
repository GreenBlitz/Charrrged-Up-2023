package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.greenblitz.tobyDetermined.Field;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Grid {
    private static Grid instance;
    private int selectedPositionID;
    private int selectedHeightID;
    private Pose2d[] locations;

    private Grid() {
        this.selectedPositionID = 0;
        this.selectedHeightID = 0;

        if (DriverStation.getAlliance() == DriverStation.Alliance.Red){
            locations = Field.PlacementLocations.getLocationsOnRedSide();
        }
        if (DriverStation.getAlliance() == DriverStation.Alliance.Blue){
            locations = Field.PlacementLocations.getLocationsOnBlueSide();
        }

    }

    public static void init(){
        if (instance == null) {
            instance = new Grid();
        }
    }

    public static Grid getInstance() {
        if (instance == null) {
            SmartDashboard.putBoolean("Grid initialized through getInstance", true);
        }
        return instance;
    }
    
    /**
     * allows you to get a pose from the Location by its index
     * fitted to the alliance
     */
    public Pose2d getSelectedPosition() {
        if (locations == null) {
            SmartDashboard.putBoolean("Invalid locations in Grid", true);
            return null;
        }
        return locations[selectedPositionID];
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
    
    public void setSelectedPositionID(int positionID){
        this.selectedPositionID = positionID;
    }
    
    public void setSelectedHeight(int heightID){
        this.selectedHeightID = heightID;
    }

    public void moveSelectedPosition(int amount){
        int newPositionID = selectedPositionID + amount;
        if (!(newPositionID >= locations.length || newPositionID < 0)){
            selectedPositionID = newPositionID;
        }
    }

    public void moveSelectedPositionRight(){
        moveSelectedPosition(DriverStation.getAlliance() == DriverStation.Alliance.Blue ? 1 : -1);
        // alternatively, can be "moveSelectedPosition(DriverStation.getAlliance().ordinal()*2 -1);"
    }

    public void moveSelectedPositionLeft(){
        moveSelectedPosition(DriverStation.getAlliance() == DriverStation.Alliance.Blue ? -1 : 1);
    }



    public enum Height{
        HIGH,
        MEDIUM,
        LOW,
    }
}