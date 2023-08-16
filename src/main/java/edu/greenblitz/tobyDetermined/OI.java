package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.commands.Funnel.ReverseRUnFunnel;
import edu.greenblitz.tobyDetermined.commands.Funnel.RunFunnel;
import edu.greenblitz.tobyDetermined.commands.GrippBall;
import edu.greenblitz.tobyDetermined.commands.HandleBalls;
//import edu.greenblitz.tobyDetermined.commands.intake.extender.ToggleRoller;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RollByConst;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RunRoller;
import edu.greenblitz.tobyDetermined.commands.shooter.RunShooterByPower;
import edu.greenblitz.tobyDetermined.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.tobyDetermined.subsystems.Indexing;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;


public class OI { //GEVALD
	
	private static OI instance;
	private static boolean isHandled = true;
	private final SmartJoystick mainJoystick;
	
	private final SmartJoystick secondJoystick;
	
	
	private OI() {
		mainJoystick = new SmartJoystick(RobotMap.Joystick.MAIN, 0.1);
		secondJoystick = new SmartJoystick(RobotMap.Joystick.SECOND, 0.2);
		//initButtons();
		buttons();
		
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
	
	
	private void initButtons() {
		//todo fill buttons
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(false));

		//shooter
		mainJoystick.X.whileTrue(new RunShooterByPower(-0.3));
		//intake
		//mainJoystick.START.whileTrue(new ToggleRoller());
		mainJoystick.R1.whileTrue(new RollByConst(0.6));
		mainJoystick.L1.whileTrue(new RollByConst(-0.6));

		//funnel
		mainJoystick.POV_UP.whileTrue(new RunFunnel());
		mainJoystick.POV_DOWN.whileTrue(new ReverseRUnFunnel());


	}

	public void buttons(){
		//Indexing.getInstance().setDefaultCommand(new HandleBalls());

		secondJoystick.B.whileTrue(new RunRoller());

		secondJoystick.A.onTrue(new GrippBall());
	}



    public SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public SmartJoystick getSecondJoystick() {
        return secondJoystick;
    }

}

