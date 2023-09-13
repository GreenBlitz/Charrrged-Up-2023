package edu.greenblitz.tobyDetermined;

import com.revrobotics.ColorSensorV3;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.Dashboard;
import edu.greenblitz.tobyDetermined.subsystems.MyShooter;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeExtender;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeRoller;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.AutonomousSelector;
import edu.greenblitz.utils.breakCoastToggle.BreakCoastSwitch;
import edu.greenblitz.utils.RoborioUtils;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
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
	ColorSensorV3 csv3 = new ColorSensorV3(I2C.Port.kOnboard);


	@Override
	public void teleopPeriodic() {


		SmartDashboard.putNumber("greeen",csv3.getGreen());
		SmartDashboard.putNumber("blue",csv3.getBlue());
		SmartDashboard.putNumber("red",csv3.getRed());

//		if (csv3.getRed()>200){
//			MyShooter.getInstace().setLowerPower(0.5);
//		} else if(csv3.getBlue()>150){
//			MyShooter.getInstace().setLowerPower(-0.5);
//		}
//		if (csv3.getBlue()<150 && csv3.getRed()<200){
//			MyShooter.getInstace().setLowerPower(0);
//		}
//
//		if(MyShooter.getInstace().getMiddlePower() > 0){
//			MyShooter.getInstace().setUpperPower(0.3);
//		}
//		if(MyShooter.getInstace().getMiddlePower() == 0){
//			MyShooter.getInstace().setUpperPower(0);
//		}
		if(csv3.getRed()>250){
			MyShooter.getInstace().redIn = true;
		}
		if(csv3.getBlue()>150 && MyShooter.getInstace().redIn == false){
			MyShooter.getInstace().activateShooterBlue();
		}
		if(csv3.getBlue()>150 && MyShooter.getInstace().redIn == true){
			SmartDashboard.putString("lowerMotorVroom","f");
		}
		if(MyShooter.getInstace().redIn == true){
			SmartDashboard.putString("redIn","true");
		} else {
			SmartDashboard.putString("redIn","false");
		}
	}
	
	/*
		TODO: Dear @Orel & @Tal, please for the love of god, use the very useful
		 function: schedule(), this will help the code to actually work
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
