package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.Dashboard;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeExtender;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeRoller;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.AutonomousSelector;
import edu.greenblitz.utils.breakCoastToggle.BreakCoastSwitch;
import edu.greenblitz.utils.RoborioUtils;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;


public class Robot extends TimedRobot {
	
	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();
		initSubsystems();
		LiveWindow.disableAllTelemetry();
		initPortForwarding();
		AutonomousSelector.getInstance();
		//swerve
		SwerveChassis.getInstance().resetChassisPose();
//		SwerveChassis.getInstance().resetAllEncoders();
		SwerveChassis.getInstance().resetEncodersByCalibrationRod();


	}
	
	@Override
	public void disabledExit() {
	}
	
	private static void initSubsystems() {
		Dashboard.init();
		Battery.init();
		SwerveChassis.init();
		IntakeExtender.init();
		IntakeRoller.init();
		OI.init();

		initToggleAbleSubsystems();
	}

	public static void initToggleAbleSubsystems (){


	}
	
	private static void initPortForwarding() {
		for (int port : RobotMap.Vision.PORT_NUMBERS) {
			PortForwarder.add(port, "photonvision.local", port);
		}
	}
	
	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
		RoborioUtils.updateCurrentCycleTime();
		SmartDashboard.putBoolean("encoderBroken", SwerveChassis.getInstance().isEncoderBroken());

		SmartDashboard.putBoolean("switch state",BreakCoastSwitch.getInstance().getSwitchState());
	}
	
	
	@Override
	public void disabledInit() {
		CommandScheduler.getInstance().cancelAll();
	}


	
	@Override
	public void teleopInit() {
		CommandScheduler.getInstance().cancelAll();

		BreakCoastSwitch.getInstance().setBreak(); //return the motors that we might have changed to break.

		Grid.init();
//		Dashboard.getInstance().activateDriversDashboard();
		SwerveChassis.getInstance().setIdleModeBrake();
		SwerveChassis.getInstance().enableVision();
	}
	
	
	@Override
	public void teleopPeriodic() {
	}
	
	
	/*
		TODO: Dear @Orel & @Tal, please for the love of god, use the very useful function: schedule(), this will help the code to actually work
   */
	@Override
	public void autonomousInit() {

	}
	
	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}
	
	@Override
	public void testPeriodic() {
	}
	
	@Override
	public void disabledPeriodic() {

	}
	
	public enum robotName {
		pegaSwerve, Frankenstein
	}

}
