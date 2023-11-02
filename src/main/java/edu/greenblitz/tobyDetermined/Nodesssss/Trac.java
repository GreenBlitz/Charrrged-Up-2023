package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.*;
import edu.wpi.first.math.util.Units;
import org.littletonrobotics.junction.Logger;
import scala.Unit;

import java.util.ArrayList;
import java.util.List;

import static edu.wpi.first.math.geometry.Rotation2d.*;
import static org.ejml.UtilEjml.assertTrue;
import static org.ejml.UtilEjml.maxInverseSize;


public class Trac {
    public static void generateTrajectory() {
        // 2018 cross scale auto waypoints.
        var sideStart = new Pose2d(Units.feetToMeters(1.54), Units.feetToMeters(23.23), fromDegrees(-180));
        var crossScale = new Pose2d(Units.feetToMeters(23.7), Units.feetToMeters(6.8), fromDegrees(-160));

        var interiorWaypoints = new ArrayList<Translation2d>();
        interiorWaypoints.add(new Translation2d(Units.feetToMeters(14.54), Units.feetToMeters(23.23)));
        interiorWaypoints.add(new Translation2d(Units.feetToMeters(21.04), Units.feetToMeters(18.23)));

        TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(12), Units.feetToMeters(12));
        config.setReversed(true);

        var trajectory = TrajectoryGenerator.generateTrajectory(
                sideStart,
                interiorWaypoints,
                crossScale,
                config);
    }

    public static void concatenate() {
        var trajectoryOne =
                TrajectoryGenerator.generateTrajectory(
                        new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
                        List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
                        new Pose2d(3, 0, Rotation2d.fromDegrees(0)),
                        new TrajectoryConfig(Units.feetToMeters(3.0), Units.feetToMeters(3.0)));

        var trajectoryTwo =
                TrajectoryGenerator.generateTrajectory(
                        new Pose2d(3, 0, Rotation2d.fromDegrees(0)),
                        List.of(new Translation2d(4, 4), new Translation2d(6, 3)),
                        new Pose2d(6, 0, Rotation2d.fromDegrees(0)),
                        new TrajectoryConfig(Units.feetToMeters(3.0), Units.feetToMeters(3.0)));

        var concatTraj = trajectoryOne.concatenate(trajectoryTwo);
        System.out.println(trajectoryOne.sample(6.79));
        System.out.println(trajectoryOne.getTotalTimeSeconds());


    }

    public static void main(String[] args) {

        var trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(4, 5, Rotation2d.fromDegrees(10)),
                List.of(new Translation2d(6, 7), new Translation2d(8, 6)),
                new Pose2d(9, 8, Rotation2d.fromDegrees(10)),
                new TrajectoryConfig(20, 15)
        );
        RectangularRegionConstraint constraint = new RectangularRegionConstraint(
                new Translation2d(1, 1),
                new Translation2d(2, 2),
                new MaxVelocityConstraint(300));


        var trajectoryTwo = TrajectoryGenerator.generateTrajectory(
                new Pose2d(2, 2,  Rotation2d.fromDegrees(30)),
                List.of( ),//new Translation2d(0.5, 1), new Translation2d(2, 3)
                new Pose2d(8, 8, Rotation2d.fromDegrees(0)),
                new TrajectoryConfig(
                        20, 15)
                        .addConstraint(constraint)
        );
        for (var point: trajectoryTwo.getStates()){
            if(constraint.isPoseInRegion(point.poseMeters)){
                System.out.println(true);
                System.out.println(point);
            }
            else {
                System.out.println(false);
                System.out.println(point);
            }
        }
        Transform2d transform2d = new Pose2d(4, 4, Rotation2d.fromDegrees(30)).minus(trajectoryTwo.getInitialPose());
        Trajectory newTrajectory = trajectoryTwo.transformBy(transform2d);
        //Pose2d bOrigin = new Pose2d(1, 1, Rotation2d.fromDegrees(0));
        //Trajectory bTrajectory = trajectory.relativeTo(bOrigin);
        //Logger.getInstance().recordOutput("Trac5", bTrajectory);
        Logger.getInstance().recordOutput("Trac4", newTrajectory);
        //Logger.getInstance().recordOutput("Trac3", trajectoryTwo.concatenate(trajectory));
        Logger.getInstance().recordOutput("Trac1", trajectoryTwo);
        //Logger.getInstance().recordOutput("Trac2", trajectory);
    }
}
