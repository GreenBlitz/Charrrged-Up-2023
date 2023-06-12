package edu.greenblitz.tobyDetermined;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.DefaultRotateWhenCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ResetExtender;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
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
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.greenblitz.utils.AutonomousSelector;
import edu.greenblitz.utils.RoborioUtils;
import edu.greenblitz.utils.SystemCheck.SystemCheck;
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
		Extender.getInstance().setIdleMode(CANSparkMax.IdleMode.kCoast);
		SwerveChassis.getInstance().resetChassisPose();
		SwerveChassis.getInstance().resetAllEncoders();
//		SwerveChassis.getInstance().resetEncodersByCalibrationRod();
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

		SystemCheck.getInstance();


		SystemCheck.getInstance().add(

				new GoToPosition(RobotMap.TelescopicArm.PresetPositions.ZIG_HAIL),
//				() -> Extender.getInstance().isAtLength() && Elbow.getInstance().isAtAngle(),
				() -> true,
				"elbow and extender"
		);
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
		SwerveChassis.getInstance().enableVision();
		Extender.getInstance().setIdleMode(CANSparkMax.IdleMode.kBrake);
		if (Extender.getInstance().DoesSensorExist && !Extender.getInstance().DidReset()) {
			new ResetExtender().schedule();
		}

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
		Grid.init();
		Extender.getInstance().setIdleMode(CANSparkMax.IdleMode.kBrake);
		MultiLimelight.getInstance().updateRobotPoseAlliance();
		Dashboard.getInstance().activateDriversDashboard();
		SwerveChassis.getInstance().setIdleModeBrake();
		SwerveChassis.getInstance().setAngleMotorsIdleMode(CANSparkMax.IdleMode.kBrake);
		SwerveChassis.getInstance().disableVision();
		ObjectSelector.selectCone();
		if (!SwerveChassis.getInstance().isEncoderBroken()) {
			SwerveChassis.getInstance().resetAllEncoders();
		} else {
			SwerveChassis.getInstance().resetEncodersByCalibrationRod();
		}
		if (Extender.getInstance().DoesSensorExist && !Extender.getInstance().DidReset()) {
			new ResetExtender().raceWith(new WaitCommand(4)).andThen(command).schedule();
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
//		if (SwerveChassis.getInstance().isEncoderBroken() || !Extender.getInstance().DidReset()) {
//			LED.getInstance().setColor(Color.kRed);
//		}else{
//			LED.getInstance().setColor(Color.kGreen);
//		}
		if (SwerveChassis.getInstance().isEncoderBroken()){
			if (Extender.getInstance().DidReset()){
				LED.getInstance().setColor(new Color(136, 8 ,90)); //dark red
			} else {
				LED.getInstance().setColor(Color.kRed);
			}
			
		} else if (!Extender.getInstance().DidReset()){
			LED.getInstance().setColor(Color.kOrangeRed);
		} else {
			LED.getInstance().setColor(Color.kGreen);
		}
	
		if(Extender.getInstance().getLimitSwitch()){
			if (Extender.getInstance().getLength() > 0 || !Extender.getInstance().DidReset()) {
				Extender.getInstance().resetLength();
			}
		}
		SwerveChassis.getInstance().isEncoderBroken();
		Elbow.getInstance().resetEncoder();
	}
	
	public enum robotName {
		pegaSwerve, Frankenstein
	}

}
