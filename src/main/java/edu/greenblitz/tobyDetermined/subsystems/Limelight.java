package edu.greenblitz.tobyDetermined.subsystems;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Optional;


public class Limelight extends GBSubsystem {
    private static Limelight instance;
    private NetworkTableEntry robotPoseEntry, jsonEntry, idEntry;
    public final static double MIN_DISTANCE_TO_FILTER_OUT = 1;

    private Limelight() {
        String robotPoseQuery = DriverStation.getAlliance() == DriverStation.Alliance.Blue ? "botpose_wpiblue" : "botpose_wpired";
        robotPoseEntry = NetworkTableInstance.getDefault().getTable(RobotMap.Vision.LIMELIGHT_NAME).getEntry(robotPoseQuery);
        jsonEntry = NetworkTableInstance.getDefault().getTable(RobotMap.Vision.LIMELIGHT_NAME).getEntry("json");
        idEntry = NetworkTableInstance.getDefault().getTable(RobotMap.Vision.LIMELIGHT_NAME).getEntry("tid");
    }

    public static Limelight getInstance() {
        if (instance == null) {
            init();
            SmartDashboard.putBoolean("limelight initialized via getinstance", true);
        }
        return instance;
    }

    public static void init(){
        instance = new Limelight();
    }


    public Optional<Pair<Pose2d, Double>> getUpdatedPoseEstimator() {
        double[] poseArray = robotPoseEntry.getDoubleArray(new double[7]);
        double processingLatency = poseArray[6]/1000;
        double timestamp = Timer.getFPGATimestamp() -  processingLatency;
        int id = (int) idEntry.getInteger(-1);

        if (id == -1){
            return Optional.empty();
        }
        Pose2d robotPose = new Pose2d(poseArray[0], poseArray[1], Rotation2d.fromDegrees(poseArray[5]));
        return Optional.of(new Pair<>(robotPose, timestamp));

    }

    public boolean hasTarget() {
        return getUpdatedPoseEstimator().isPresent();
    }



}





