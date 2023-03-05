package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.commands.ConsoleLog;
import edu.greenblitz.tobyDetermined.commands.LED.EncoderBrokenLED;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.DefaultRotateWhenCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ResetExtender;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.Dashboard;
import edu.greenblitz.tobyDetermined.subsystems.LED;
import edu.greenblitz.tobyDetermined.subsystems.Limelight.MultiLimelight;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.RotatingBelly;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeExtender;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeRoller;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.AutonomousSelector;
import edu.greenblitz.utils.RoborioUtils;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.WaitCommand;


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
		SwerveChassis.getInstance().resetAllEncoders();
	}
	
	@Override
	public void disabledExit() {
	}
	
	private static void initSubsystems() {
		MultiLimelight.init();
		Dashboard.init();
		LED.init();
		Battery.init();
		Extender.init();
		Elbow.init();
		Claw.init();
		SwerveChassis.init();
		RotatingBelly.init();
		IntakeExtender.init();
		IntakeRoller.init();
		OI.init();
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
	}
	
	
	@Override
	public void disabledInit() {
		CommandScheduler.getInstance().cancelAll();
	}
	
	@Override
	
	public void teleopInit() {
		CommandScheduler.getInstance().cancelAll();
		
		Grid.init();
		MultiLimelight.getInstance().updateRobotPoseAlliance();
		Dashboard.getInstance().activateDriversDashboard();
		SwerveChassis.getInstance().setIdleModeBrake();
		if (Extender.getInstance().DoesSensorExist) {
			new ResetExtender().raceWith(new WaitCommand(2.5).andThen(new ConsoleLog("time out", "arm reset time out"))).schedule();
		}

//		new SensorlessReset().schedule();
		Claw.getInstance().setDefaultCommand(new DefaultRotateWhenCube());
	}
	
	
	@Override
	public void teleopPeriodic() {
	}
	
	
	/*
		TODO: Dear @Orel & @Tal, please for the love of god, use the very useful function: schedule(), this will help the code to actually work
   */
	@Override
	public void autonomousInit() {
		Command command = AutonomousSelector.getInstance().getChosenValue().autonomousCommand;
		Claw.getInstance().coneCatchMode();
		Grid.init();
		MultiLimelight.getInstance().updateRobotPoseAlliance();
		Dashboard.getInstance().activateDriversDashboard();
		SwerveChassis.getInstance().setIdleModeBrake();
		if (Extender.getInstance().DoesSensorExist) {
			new ResetExtender().raceWith(new WaitCommand(3).andThen(new ConsoleLog("time out", "arm reset time out"))).andThen(command).schedule();
		} else command.schedule();
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
		if (SwerveChassis.getInstance().isEncoderBroken()) /*new EncoderBrokenLED().schedule()*/ {
			LED.getInstance().setColor(Color.kRed);
		}else {
			SwerveChassis.getInstance().resetAllEncoders();
			LED.getInstance().setColor(Color.kGreen);
		}
	}
	
	public enum robotName {
		pegaSwerve, Frankenstein
	}
}
