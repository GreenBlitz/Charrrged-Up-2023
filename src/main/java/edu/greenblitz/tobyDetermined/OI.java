package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.commands.Funnel.ReverseRUnFunnel;
import edu.greenblitz.tobyDetermined.commands.Funnel.RunFunnel;
import edu.greenblitz.tobyDetermined.commands.HandleMyballs;
import edu.greenblitz.tobyDetermined.commands.Shooting;
import edu.greenblitz.tobyDetermined.commands.intake.roller.ReverseRunRoller;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RunRoller;
import edu.greenblitz.tobyDetermined.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.tobyDetermined.subsystems.ThingsToHandleBalls;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
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
		init();
		return instance;
	}
	
	public static void init() {
		if (instance == null) {
			instance = new OI();
		}
	}
	
	public static boolean isIsHandled() {
		return isHandled;
	}
	
	public static void disableHandling() {
		isHandled = false;
	}
	
	public void butt(){
		mainJoystick.A.whileTrue(new RunRoller());
		mainJoystick.X.onTrue(new Shooting());
		mainJoystick.POV_DOWN.whileTrue(new RunFunnel());
		mainJoystick.POV_UP.whileTrue(new ReverseRUnFunnel());
		mainJoystick.Y.whileTrue(new ReverseRunRoller());
	}
	private void initButtons() {
		butt();
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(true));
		ThingsToHandleBalls.getInstance().setDefaultCommand(new HandleMyballs());
		


	}



    public SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public SmartJoystick getSecondJoystick() {
        return secondJoystick;
    }

}

