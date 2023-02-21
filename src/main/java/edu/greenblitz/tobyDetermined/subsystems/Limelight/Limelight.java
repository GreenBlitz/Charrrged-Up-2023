package edu.greenblitz.tobyDetermined.subsystems.Limelight;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Optional;


class Limelight {
    private NetworkTableEntry robotPoseEntry, idEntry;

    Limelight(String limelightName) {
        String robotPoseQuery = "botpose_wpiblue";
        robotPoseEntry = NetworkTableInstance.getDefault().getTable(limelightName).getEntry(robotPoseQuery);
        idEntry = NetworkTableInstance.getDefault().getTable(limelightName).getEntry("tid");
    }



    public Optional<Pair<Pose2d, Double>> getUpdatedPoseEstimation() {
        //the botpose array is comprised of {0:x, 1:y, 2:z, 3:Roll, 4:Pitch, 5:Yaw, 6:total latency from capture to send}
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
        return getUpdatedPoseEstimation().isPresent();
    }



}





