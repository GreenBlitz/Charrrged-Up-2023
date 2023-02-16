package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.commands.Auto.PathFollowerBuilder;
import edu.greenblitz.tobyDetermined.commands.BatteryDisabler;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.commands.LED.BackgroundColor;
import edu.greenblitz.tobyDetermined.subsystems.*;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.Dashboard;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeGameObjectSensor;
import edu.greenblitz.tobyDetermined.subsystems.Photonvision;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.AutonomousSelector;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

    @Override
    public void robotInit() {
        CommandScheduler.getInstance().enable();
		initSubsystems();
        LiveWindow.disableAllTelemetry();
        Battery.getInstance().setDefaultCommand(new BatteryDisabler());
        Photonvision.getInstance();

        LiveWindow.disableAllTelemetry();

        LED.getInstance().setDefaultCommand(new BackgroundColor());
        //swerve

        SwerveChassis.getInstance().resetChassisPose();
        SwerveChassis.getInstance().resetAllEncoders();
    }
	
	private static void initSubsystems(){
        LimeLight.init();
        Dashboard.init();
		Battery.getInstance().setDefaultCommand(new BatteryDisabler());
		IntakeGameObjectSensor.init();
		Grid.init();
		//swerve

		SwerveChassis.getInstance().resetChassisPose();
		SwerveChassis.getInstance().resetAllEncoders();
		OI.getInstance();
	}
	
	private static void initPortForwarding() {
		for (int port:RobotMap.Vision.portNumbers) {
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
        PathFollowerBuilder.getInstance().followPath(AutonomousSelector.getInstance().getChosenValue()).schedule();
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
