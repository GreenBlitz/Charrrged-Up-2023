package edu.greenblitz.tobyDetermined;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.*;

import java.util.ArrayList;
import java.util.List;

public class Field {
    /**
     * gets pose, returns it fitted to the other alliance ("mirrored") and rotated by 180 degrees.
     * */
    public static Pose2d mirrorPositionToOtherSide(Pose2d pose){
        Pose2d mirroredPose = new Pose2d(
                FieldConstants.fieldLength - pose.getX(),
                pose.getY(),
                new Rotation2d(pose.getRotation().getRadians() + Math.PI)); //rotates by 180 degrees;
        return mirroredPose;
    }

    /**
     * gets pose[], returns it fitted to the other alliance ("mirrored") and rotated by 180 degrees.
     * */
    public static Pose2d[] mirrorPositionsToOtherSide(Pose2d[] poses){
        Pose2d[] mirroredPoses = new Pose2d[poses.length];
        for (int i = 0; i< poses.length; i++ ) {
            mirroredPoses[i] = mirrorPositionToOtherSide(poses[i]);
        }
        return mirroredPoses;
    }


    public static class Apriltags{
        public static int selectedTagId = 1;
        public static final Pose3d redApriltagLocationId1 = new Pose3d(new Translation3d(15.513558, 1.071626, 0), new Rotation3d(0, 0, Math.PI));
        public static final Pose3d redApriltagLocationId2 = new Pose3d(new Translation3d(15.513558, 2.748026, 0), new Rotation3d(0, 0, Math.PI));
        public static final Pose3d redApriltagLocationId3 = new Pose3d(new Translation3d(15.513558, 4.424426, 0), new Rotation3d(0, 0, Math.PI));
        public static final Pose3d blueApriltagLocationId1 = new Pose3d(new Translation3d(1.02743, 4.424426, 0), new Rotation3d(0, 0, Math.PI));
        public static final Pose3d blueApriltagLocationId2 = new Pose3d(new Translation3d(1.02743, 2.748026, 0), new Rotation3d(0, 0, Math.PI));
        public static final Pose3d blueApriltagLocationId3 = new Pose3d(new Translation3d(1.02743, 1.071626, 0), new Rotation3d(0, 0, Math.PI));
        static List<AprilTag> apriltags = new ArrayList<>(9) ;

        static {
            apriltags.add(new AprilTag(1, redApriltagLocationId1));
            apriltags.add(new AprilTag(2,redApriltagLocationId2));
            apriltags.add(new AprilTag(3,redApriltagLocationId3));
            apriltags.add(new AprilTag(6,blueApriltagLocationId1));
            apriltags.add(new AprilTag(7,blueApriltagLocationId2));
            apriltags.add(new AprilTag(8,blueApriltagLocationId3));
        }
        public static final AprilTagFieldLayout aprilTagFieldLayout = new AprilTagFieldLayout(apriltags,10,10);

    }
    public static class PlacementLocations{

        private static final Pose2d[] locationsOnBlueSide = {
                new Pose2d(new Translation2d(1.3 + (0.5 * RobotMap.Swerve.TobyDetermined.ROBOT_LENGTH_IN_METERS), 0.508), new Rotation2d()),
                new Pose2d(new Translation2d(1.3 + (0.5 * RobotMap.Swerve.TobyDetermined.ROBOT_LENGTH_IN_METERS), 1.067), new Rotation2d()),
                new Pose2d(new Translation2d(1.3 + (0.5 * RobotMap.Swerve.TobyDetermined.ROBOT_LENGTH_IN_METERS), 1.626), new Rotation2d()),
                new Pose2d(new Translation2d(1.3 + (0.5 * RobotMap.Swerve.TobyDetermined.ROBOT_LENGTH_IN_METERS), 2.184), new Rotation2d()),
                new Pose2d(new Translation2d(1.3 + (0.5 * RobotMap.Swerve.TobyDetermined.ROBOT_LENGTH_IN_METERS), 2.743), new Rotation2d()),
                new Pose2d(new Translation2d(1.3 + (0.5 * RobotMap.Swerve.TobyDetermined.ROBOT_LENGTH_IN_METERS), 3.302), new Rotation2d()),
                new Pose2d(new Translation2d(1.3 + (0.5 * RobotMap.Swerve.TobyDetermined.ROBOT_LENGTH_IN_METERS), 3.861), new Rotation2d()),
                new Pose2d(new Translation2d(1.3 + (0.5 * RobotMap.Swerve.TobyDetermined.ROBOT_LENGTH_IN_METERS), 4.420), new Rotation2d()),
                new Pose2d(new Translation2d(1.3 + (0.5 * RobotMap.Swerve.TobyDetermined.ROBOT_LENGTH_IN_METERS), 4.978), new Rotation2d())
        };

        public static Pose2d[] getLocationsOnBlueSide(){
            return locationsOnBlueSide;
        }

        public static Pose2d[] getLocationsOnRedSide(){
            return mirrorPositionsToOtherSide(locationsOnBlueSide);
        }


    }
    public static class FieldConstants{
        public final static double fieldLength = 16.54175;
        public final static double fieldWidth = 8.0137;
    }
}
