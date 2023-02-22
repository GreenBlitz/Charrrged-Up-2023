package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.commands.Auto.PathFollowerBuilder;
import edu.greenblitz.tobyDetermined.commands.BatteryDisabler;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.commands.LED.BackgroundColor;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToPose;
import edu.greenblitz.tobyDetermined.subsystems.*;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.Dashboard;
import edu.greenblitz.tobyDetermined.subsystems.Limelight.MultiLimelight;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.BellyGameObjectSensor;
import edu.greenblitz.tobyDetermined.subsystems.Limelight;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.RotatingBelly;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeExtender;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeRoller;
>>>>>>> master
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.AutonomousSelector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
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
		initSubsystems();
		initPortForwarding();
        LiveWindow.disableAllTelemetry();
        Battery.getInstance().setDefaultCommand(new BatteryDisabler());
        LiveWindow.disableAllTelemetry();
	    initPortForwarding();
	    LiveWindow.disableAllTelemetry();
	    Battery.getInstance().setDefaultCommand(new BatteryDisabler());
        LiveWindow.disableAllTelemetry();
        AutonomousSelector.getInstance();

        LED.getInstance().setDefaultCommand(new BackgroundColor());
        //swerve

        SwerveChassis.getInstance().resetChassisPose();
        SwerveChassis.getInstance().resetAllEncoders();
    }
	
	private static void initSubsystems(){
        MultiLimelight.init();
        Dashboard.init();
		BellyGameObjectSensor.init();
		Grid.init();
		Limelight.init();
		LED.init();
		Battery.init();
		Extender.init();
		Elbow.init();
		Claw.init();
		SwerveChassis.init();
		RotatingBelly.init();
		BellyGameObjectSensor.init();
		IntakeExtender.init();
		IntakeRoller.init();
		OI.init();
	}
	
	private static void initPortForwarding() {
		for (int port:RobotMap.Vision.PORT_NUMBERS) {
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
        AutonomousSelector.getInstance().getChosenValue().autonomousCommand.schedule();
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
