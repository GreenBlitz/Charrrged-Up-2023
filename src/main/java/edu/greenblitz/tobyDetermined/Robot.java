package edu.greenblitz.tobyDetermined;


import edu.greenblitz.tobyDetermined.commands.BatteryDisabler;
import edu.greenblitz.tobyDetermined.commands.prototypes.MovePrototypes;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.Dashboard;
import edu.greenblitz.tobyDetermined.subsystems.Limelight;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import java.util.ArrayList;
import java.util.HashMap;

public class Robot extends TimedRobot {

	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();
		Dashboard.init();
		Limelight.getInstance();
		initPortForwarding();
		LiveWindow.disableAllTelemetry();
		Battery.getInstance().setDefaultCommand(new BatteryDisabler());
		
		//swerve
		
		SwerveChassis.getInstance().resetChassisPose();
		SwerveChassis.getInstance().resetAllEncoders();
		OI.getInstance();
		prototypes = MovePrototypes.initPrototypes(3);
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
	
	ArrayList<SendableChooser<String>> prototypes;
	@Override
	public void teleopInit() {
		CommandScheduler.getInstance().cancelAll();
		new MovePrototypes(3, prototypes).schedule();

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
}
