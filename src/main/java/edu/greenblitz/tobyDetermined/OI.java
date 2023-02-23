package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.*;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveSelectedTargetLeft;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveSelectedTargetRight;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.*;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.DynamicFeedForward;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.QuasiStaticFeedForward;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.*;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.*;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.tobyDetermined.commands.intake.extender.ExtendRoller;
import edu.greenblitz.tobyDetermined.commands.intake.extender.RetractRoller;
import edu.greenblitz.tobyDetermined.commands.intake.extender.ToggleRoller;
import edu.greenblitz.tobyDetermined.commands.swerve.ToggleBrakeCoast;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;

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



	private void initButtons() {
//		Extender.getInstance().setDefaultCommand(new moveByJoysticks());
//		Elbow.getInstance().setDefaultCommand(new elbowMoveByJoysticks());
//		mainJoystick.A.onTrue(new InstantCommand(() -> Claw.getInstance().toggleSolenoid()));
//		mainJoystick.B.whileTrue(new StartEndCommand(() -> Claw.getInstance().motorGrip(), () -> Claw.getInstance().stopMotor()));
//		mainJoystick.Y.whileTrue(new StartEndCommand(() -> Claw.getInstance().motorEject(), () -> Claw.getInstance().stopMotor()));
		mainJoystick.A.whileTrue(new StartEndCommand(() -> Elbow.getInstance().debugSetPower(0.3), () -> Elbow.getInstance().stop(), Elbow.getInstance()));
		mainJoystick.B.whileTrue(new StartEndCommand(() -> Elbow.getInstance().debugSetPower(-0.3), () -> Elbow.getInstance().stop(), Elbow.getInstance()));
		mainJoystick.X.whileTrue(new StartEndCommand(() -> Extender.getInstance().debugSetPower(0.5), () -> Extender.getInstance().stop(), Extender.getInstance()));
		mainJoystick.Y.whileTrue(new StartEndCommand(() -> Extender.getInstance().debugSetPower(-0.5), () -> Extender.getInstance().stop(), Extender.getInstance()));
		mainJoystick.POV_UP.toggleOnTrue(new ExtendToLength(0.20));
		mainJoystick.POV_LEFT.toggleOnTrue(new RotateToAngleRadians(Units.degreesToRadians(30)));
		mainJoystick.START.onTrue(new InstantCommand(() -> Extender.getInstance().resetLength()));
	
	}

	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}

	public SmartJoystick getSecondJoystick() {
		return secondJoystick;
	}
}

