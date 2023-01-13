package edu.greenblitz.tobyDetermined;


import edu.greenblitz.tobyDetermined.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveByVisionSupplier;
import edu.greenblitz.tobyDetermined.subsystems.LED;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.util.Color;
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
		mainJoystick.Y.whileTrue(new InstantCommand(){
			@Override
			public void execute() {
				LED.getInstance().setColor(new Color(255,0,0));
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