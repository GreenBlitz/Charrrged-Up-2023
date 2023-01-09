package edu.greenblitz.tobyDetermined;


import edu.greenblitz.tobyDetermined.commands.prototypes.NeoMoveByPower;
import edu.greenblitz.tobyDetermined.commands.prototypes.TalonMoveByPower;
import edu.greenblitz.utils.hid.SmartJoystick;

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
		mainJoystick.Y.whenHeld(new NeoMoveByPower(0.1,4));
		
	}
	
	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}
	
	public SmartJoystick getSecondJoystick() {
		return secondJoystick;
	}
	
}