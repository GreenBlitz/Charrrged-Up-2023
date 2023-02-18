package edu.greenblitz.tobyDetermined.subsystems;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.greenblitz.tobyDetermined.Field;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;

import java.util.Optional;


public class Limelight extends GBSubsystem {
    private static Limelight instance;
    private NetworkTableEntry robotPoseEntry, jsonEntry, idEntry;

    private double tollerance = 1;

    private Limelight() {
        robotPoseEntry = NetworkTableInstance.getDefault().getTable(RobotMap.Vision.LIMELIGHT_NAME).getEntry("botpose");
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
        double timestamp = getTimeStamp();
        int id = (int) idEntry.getInteger(-1);
        if (poseArray[0]  >= SwerveChassis.getInstance().getRobotPose().getX() + tollerance || poseArray[1] >= SwerveChassis.getInstance().getRobotPose().getY() + tollerance){
            return Optional.empty();
        }
        if (id == -1){
            return Optional.empty();
        }
        Pose2d robotPose = new Pose2d(poseArray[0], poseArray[1], Rotation2d.fromDegrees(poseArray[5]));
        return Optional.of(new Pair<>(robotPose, timestamp));
    }

    public boolean hasTarget() {
        return getUpdatedPoseEstimator().isPresent();
    }


    /**
     * dont ask why dons ask how this came from online this gets timestamp
     * @return
     */
    public double getTimeStamp(){
        String jsonDump = jsonEntry.getString("{}");

        double currentTimeStampSeconds = 0;
        // Attempts to get the time stamp for when the robot pose was calculated
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNodeData = mapper.readTree(jsonDump);
            double timeStampValue = jsonNodeData.path("Results").path("ts").asDouble();
            // Converts from milliseconds to seconds
            currentTimeStampSeconds = timeStampValue / 1000;
        } catch (JsonProcessingException e) {
            SmartDashboard.putString("Json Parsing Error", e.toString());
        }
        return currentTimeStampSeconds;
    }


}





