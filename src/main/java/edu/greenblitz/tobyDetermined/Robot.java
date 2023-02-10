package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.commands.Auto.PathFollowerBuilder;
import edu.greenblitz.tobyDetermined.commands.BatteryDisabler;
import edu.greenblitz.tobyDetermined.commands.LED.BackgroundColor;
import edu.greenblitz.tobyDetermined.commands.LED.DefaultColor;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToPos;
import edu.greenblitz.tobyDetermined.subsystems.*;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.AutonomousSelector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Robot extends TimedRobot {

    @Override
    public void robotInit() {
        CommandScheduler.getInstance().enable();
        Dashboard.init();
        Limelight.getInstance();
        initPortForwarding();
        LiveWindow.disableAllTelemetry();
        Battery.getInstance().setDefaultCommand(new BatteryDisabler());
        AutonomousSelector.getInstance();
        IntakeGameObjectSensor.getInstance().periodic();

        LED.getInstance().setDefaultCommand(new BackgroundColor());
        //swerve

        SwerveChassis.getInstance().resetChassisPose();
        SwerveChassis.getInstance().resetAllEncoders();
        OI.getInstance();
    }

    private static void initPortForwarding() {
        for (int port : RobotMap.Vision.portNumbers) {
            PortForwarder.add(port, "photonvision.local", port);
        }
    }


    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }


    @Override
    public void disabledInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override

    public void teleopInit() {
        CommandScheduler.getInstance().cancelAll();
        SwerveChassis.getInstance().setIdleModeBrake();
    }

    @Override
    public void teleopPeriodic() {
    }


    /*
        TODO: Dear @Orel & @Tal, please for the love of god, use the very useful function: schedule(), this will help the code to actually work
    */
    @Override
    public void autonomousInit() {

        new SequentialCommandGroup(
                PathFollowerBuilder.getInstance()
                        .followPath("2"),
                new MoveToPos(new Pose2d(1.8,4.4,new Rotation2d(180)), true)
        ).schedule();
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
    }

    public enum robotName {
        pegaSwerve, TobyDetermined
    }
}
