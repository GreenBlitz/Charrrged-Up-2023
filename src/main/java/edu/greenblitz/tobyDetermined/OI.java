package edu.greenblitz.tobyDetermined;


import edu.greenblitz.tobyDetermined.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveByVisionSupplier;
import edu.greenblitz.tobyDetermined.subsystems.LED;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class OI { //GEVALD
	
	private static OI instance;
	private static boolean isHandled = true;
	private final SmartJoystick mainJoystick;
	
	private final SmartJoystick secondJoystick;
	
	
	private OI() {
		mainJoystick = new SmartJoystick(RobotMap.Joystick.MAIN, 0.1);
		secondJoystick = new SmartJoystick(RobotMap.Joystick.SECOND, 0.2);
		initButtons();
		
	}
	
	public static OI getInstance() {
		if (instance == null) {
			instance = new OI();
		}
		return instance;
	}
	
	public static boolean isIsHandled() {
		return isHandled;
	}
	
	public static void disableHandling() {
		isHandled = false;
	}
	
	private void initButtons() {

		mainJoystick.Y.whileTrue(new GBCommand(){

			@Override
			public void initialize() {
				LED.getInstance().setColor(Color.kTurquoise);
			}
		});
		mainJoystick.A.whileTrue(new GBCommand(){

			@Override
			public void initialize() {
				LED.getInstance().setColor(Color.kRed);
			}
		});
		mainJoystick.B.whileTrue(new GBCommand(){

			@Override
			public void initialize() {
				LED.getInstance().setColor(Color.kGreen);
			}
		});
		mainJoystick.X.whileTrue(new GBCommand(){

			@Override
			public void initialize() {
				LED.getInstance().setColor(Color.kGold);
			}
		});
		mainJoystick.POV_DOWN.whileTrue(new GBCommand(){

			@Override
			public void initialize() {
				LED.getInstance().setColor(Color.kWhiteSmoke);
			}
		});
		mainJoystick.POV_RIGHT.whileTrue(new GBCommand(){

			@Override
			public void initialize() {
				LED.getInstance().setColor(Color.kMagenta);
			}
		});
		mainJoystick.POV_UP.whileTrue(new GBCommand(){

			@Override
			public void initialize() {
				LED.getInstance().setColor(new Color(0,0,0));
			}
		});
	}
	
	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}
	public SmartJoystick getSecondJoystick() {
		return secondJoystick;
	}
	
}