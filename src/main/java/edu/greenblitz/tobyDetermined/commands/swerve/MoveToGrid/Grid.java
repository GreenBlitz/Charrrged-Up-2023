package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.greenblitz.tobyDetermined.Field;
import edu.greenblitz.tobyDetermined.Robot;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBMath;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Grid {
    private static Grid instance;
    private int selectedPositionID;
    private int selectedHeightID;
    private static Pose2d[] locations;

    private Grid() {
        this.selectedPositionID = 0;
        this.selectedHeightID = 0;

        updateAlliance();
    }

    public void updateAlliance(){
        if (DriverStation.getAlliance() == DriverStation.Alliance.Red){
            locations = Field.PlacementLocations.getLocationsOnRedSide();
            SmartDashboard.putString("alliance", "red");
        }
        if (DriverStation.getAlliance() == DriverStation.Alliance.Blue){
            locations = Field.PlacementLocations.getLocationsOnBlueSide();
            SmartDashboard.putString("alliance", "blue");
        }
    }

    public static void init(){
        if (instance == null) {
            instance = new Grid();
        }
    }

    public static Grid getInstance() {
        init();
        return instance;
    }

    /**
     * allows you to get a pose from the Location by its index
     * fitted to the alliance
     */
    public Pose2d getSelectedPosition() {
        updateAlliance();
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

    public RobotMap.TelescopicArm.PresetPositions getSelectedConePosition(){
        switch (getSelectedHeight()){
            case LOW:
                return RobotMap.TelescopicArm.PresetPositions.LOW;
            case MEDIUM:
                return RobotMap.TelescopicArm.PresetPositions.CONE_MID;
            case HIGH:
                return RobotMap.TelescopicArm.PresetPositions.CONE_HIGH;
        }
        return RobotMap.TelescopicArm.PresetPositions.CUBE_HIGH; //todo - make return HAIL in merge <3
    }

    public RobotMap.TelescopicArm.PresetPositions getSelectedCubePosition(){
        switch (getSelectedHeight()){
            case LOW:
                return RobotMap.TelescopicArm.PresetPositions.LOW;
            case MEDIUM:
                return RobotMap.TelescopicArm.PresetPositions.CUBE_MID;
            case HIGH:
                return RobotMap.TelescopicArm.PresetPositions.CUBE_HIGH;
        }
        return null; //todo make not null </3
    }

    public void rewriteArmPositionInSelectedPose(double angleInRads, double length){
        angleInRads= ((int)(angleInRads*1000)) / 1000.0;
        length= ((int)(length*1000)) / 1000.0;
        Console.log("reWrite" + getSelectedHeight()+" "+(Field.PlacementLocations.isGridPositionIDofCube(getSelectedPositionID())? "cube": "cone"),"angle:" +angleInRads+ "|length:"+ length);
        switch (getSelectedHeight()){
            case LOW:
                    RobotMap.TelescopicArm.PresetPositions.LOW.angleInRadians = angleInRads;
                    RobotMap.TelescopicArm.PresetPositions.LOW.distance = length;
                    break;
            case MEDIUM:
                if(Field.PlacementLocations.isGridPositionIDofCube(getSelectedPositionID())){
                    RobotMap.TelescopicArm.PresetPositions.CUBE_MID.angleInRadians = angleInRads;
                    RobotMap.TelescopicArm.PresetPositions.CUBE_MID.distance = length;
                } else {
                    RobotMap.TelescopicArm.PresetPositions.CONE_MID.angleInRadians = angleInRads;
                    RobotMap.TelescopicArm.PresetPositions.CONE_MID.distance = length;
                }
                break;
            case HIGH:
                if(Field.PlacementLocations.isGridPositionIDofCube(getSelectedPositionID())){
                    RobotMap.TelescopicArm.PresetPositions.CUBE_HIGH.angleInRadians = angleInRads;
                    RobotMap.TelescopicArm.PresetPositions.CUBE_HIGH.distance = length;
                }else {
                    RobotMap.TelescopicArm.PresetPositions.CONE_HIGH.angleInRadians = angleInRads;
                    RobotMap.TelescopicArm.PresetPositions.CONE_HIGH.distance = length;
                }
                break;
        }
    }


    public void setSelectedPositionID(int positionID){
        this.selectedPositionID = positionID;
    }

    public void setSelectedHeight(int heightID){
        this.selectedHeightID = heightID;
    }

    public void moveSelectedPosition(int amount){
        updateAlliance();
        int newPositionID = selectedPositionID + amount;
        selectedPositionID = (int) GBMath.absoluteModulo(newPositionID,9);
    }

    public void moveSelectedPositionRight(){
        moveSelectedPosition(DriverStation.getAlliance() == DriverStation.Alliance.Blue ? -1 : 1);
    }

    public void moveSelectedPositionLeft(){
        moveSelectedPosition(DriverStation.getAlliance() == DriverStation.Alliance.Blue ? 1 : -1);
    }

    public void moveSelectedHeight(int amount){
        int newPositionID = selectedHeightID + amount;
        selectedHeightID = (int) GBMath.absoluteModulo(newPositionID,3);
    }

    public void moveSelectedHeightUp(){
        moveSelectedHeight(1);
    }

    public void moveSelectedHeightDown(){
        moveSelectedHeight(-1);
    }

    public RobotMap.TelescopicArm.PresetPositions getArmPositionByObject(){
        boolean isConeIn = Field.PlacementLocations.isGridPositionIDofCone(Grid.getInstance().getSelectedPositionID());
        switch (getSelectedHeight()){
            case LOW:
                return RobotMap.TelescopicArm.PresetPositions.LOW;
            case MEDIUM:
                return isConeIn? RobotMap.TelescopicArm.PresetPositions.CONE_MID : RobotMap.TelescopicArm.PresetPositions.CUBE_MID;
            case HIGH:
                return isConeIn? RobotMap.TelescopicArm.PresetPositions.CONE_HIGH : RobotMap.TelescopicArm.PresetPositions.CUBE_HIGH;
        }
        return RobotMap.TelescopicArm.PresetPositions.PRE_INTAKE_GRAB_POSITION;
    }



    public enum Height{
        LOW,
        MEDIUM,
        HIGH
    }
}