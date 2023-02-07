package edu.greenblitz.tobyDetermined;


import edu.greenblitz.tobyDetermined.commands.LED.DefaultColor;
import edu.greenblitz.tobyDetermined.commands.LED.ObjectInClawLED;
import edu.greenblitz.tobyDetermined.commands.LED.ObjectInIntakeLED;
import edu.greenblitz.tobyDetermined.commands.LED.HumanPlayerObjectIndicator;
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

		mainJoystick.B.whileTrue(new ObjectInIntakeLED());
		mainJoystick.Y.whileTrue(new ObjectInClawLED());
		mainJoystick.X.whileTrue(new HumanPlayerObjectIndicator(HumanPlayerObjectIndicator.wantedObject.CUBE));
		mainJoystick.A.whileTrue(new HumanPlayerObjectIndicator(HumanPlayerObjectIndicator.wantedObject.CONE));
		mainJoystick.POV_DOWN.whileTrue(new DefaultColor());
	}
	
	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}
	public SmartJoystick getSecondJoystick() {
		return secondJoystick;
	}
	
}