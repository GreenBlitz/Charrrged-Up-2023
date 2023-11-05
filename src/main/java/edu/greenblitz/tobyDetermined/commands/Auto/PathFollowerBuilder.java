package edu.greenblitz.tobyDetermined.commands.Auto;


import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassis;
import edu.wpi.first.wpilibj2.command.*;

public class PathFollowerBuilder {

    public static final double DEADLINE_TIME_FOR_PRE_AUTO_COMMAND = 1;


    private static PathFollowerBuilder instance;

    private PathFollowerBuilder() {
        AutoBuilder.configureHolonomic(
                SwerveChassis.getInstance()::getRobotPose,
                SwerveChassis.getInstance()::resetChassisPose,
                SwerveChassis.getInstance()::getRobotRelativeChassisSpeeds,
                SwerveChassis.getInstance()::moveByRobotRelativeSpeeds,
                RobotMap.Swerve.Autonomus.CONFIG,
                SwerveChassis.getInstance()
        );
    }


    public static PathFollowerBuilder getInstance() {
        if (instance == null) {
            instance = new PathFollowerBuilder();
        }
        return instance;
    }

    public Command getAutonomousCommand(String autoName) {
        return new PathPlannerAuto(autoName);
    }


}
