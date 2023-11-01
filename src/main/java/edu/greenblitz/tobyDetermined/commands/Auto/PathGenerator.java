package edu.greenblitz.tobyDetermined.commands.Auto;

import com.pathplanner.lib.commands.PathfindThenFollowPathHolonomic;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPoint;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassis;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.LinkedList;
import java.util.List;

public class PathGenerator {

    public static Command getPathCommand(Pose2d startPoint, Pose2d endPoint, PathConstraints constraints){
        return new PathfindThenFollowPathHolonomic(
                new PathPlannerPath(
                        PathPlannerPath.bezierFromPoses(startPoint,endPoint),
                        constraints,
                        new GoalEndState(0,endPoint.getRotation())
                ),
                constraints,
                SwerveChassis.getInstance()::getRobotPose,
                SwerveChassis.getInstance()::getRobotRelativeChassisSpeeds,
                SwerveChassis.getInstance()::moveByRobotRelativeSpeeds,
                RobotMap.Swerve.Frankenstein.PATH_FOLLOWER_CONFIG,
                0,
                SwerveChassis.getInstance()
        );
    }
}
