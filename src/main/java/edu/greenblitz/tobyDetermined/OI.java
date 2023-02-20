package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.commands.LED.HumanPlayerObjectIndicator;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.FullGripAndPutInClaw;
import edu.greenblitz.tobyDetermined.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.*;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveSelectedTargetLeft;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveSelectedTargetRight;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.GoToPosition;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.EjectFromClaw;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToGrid;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.tobyDetermined.commands.intake.extender.ExtendRoller;
import edu.greenblitz.tobyDetermined.commands.intake.extender.RetractRoller;
import edu.greenblitz.tobyDetermined.commands.intake.extender.ToggleRoller;
import edu.greenblitz.tobyDetermined.commands.swerve.ToggleBrakeCoast;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
			init();
			SmartDashboard.putBoolean("oi initialized via getinstance", true);
		}
		return instance;
	}

	public static void init() {
		instance = new OI();
	}

	public static boolean isIsHandled() {
		return isHandled;
	}

	public static void disableHandling() {
		isHandled = false;
	}

	public double countB = 0;


	private void initButtons() {
	}

	private void amirButtons(){
		secondJoystick.X.onTrue(new FullGripAndPutInClaw());
		secondJoystick.Y.onTrue(new EjectFromClaw());
		secondJoystick.B.onTrue(new GoToPosition((RobotMap.TelescopicArm.PresetPositions.INTAKE_DROP_POSITION)));//TODO: zig hail
		secondJoystick.POV_LEFT.onTrue(new MoveSelectedTargetLeft());
		secondJoystick.POV_RIGHT.onTrue(new MoveSelectedTargetRight());
		secondJoystick.POV_UP.onTrue(new MoveSelectedTargetUp());
		secondJoystick.POV_DOWN.onTrue(new MoveSelectedTargetDown());
		secondJoystick.A.onTrue(new GoToGrid());
		secondJoystick.BACK.onTrue(new HumanPlayerObjectIndicator(HumanPlayerObjectIndicator.wantedObject.CUBE));
		secondJoystick.START.onTrue(new HumanPlayerObjectIndicator(HumanPlayerObjectIndicator.wantedObject.CONE));
	}

	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}

	public SmartJoystick getSecondJoystick() {
		return secondJoystick;
	}
}

