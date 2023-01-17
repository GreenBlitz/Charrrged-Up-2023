package edu.greenblitz.tobyDetermined.commands.Auto;


import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PathPlannerCommand extends SwerveControllerCommand {


    public PathPlannerCommand(PathPlannerTrajectory trajectory) {
        super(
                trajectory,
                SwerveChassis.getInstance()::getlocation,
                SwerveChassis.getInstance().getKinematics(),
                createPIDController(),
                createPIDController(),
                createThetaController(),
                SwerveChassis.getInstance()::setModuleStates,
                SwerveChassis.getInstance());
        System.out.println("starting auto");
    }


    private static ProfiledPIDController createThetaController() {
        ProfiledPIDController thetaController = new ProfiledPIDController(
                RobotMap.Swerve.SdsSwerve.angPID.getKp(), 0, 0, new TrapezoidProfile.Constraints(RobotMap.Swerve.MAX_ANGULAR_SPEED, 1));
        thetaController.enableContinuousInput(-Math.PI, Math.PI);
        return thetaController;
    }


    private static PIDController createPIDController() {
        return new PIDController(RobotMap.Swerve.SdsSwerve.linPID.getKp(), 0, 0);
    }
}
