package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.commands.LED.HumanPlayerObjectIndicator;
import edu.greenblitz.tobyDetermined.commands.LED.SetConeLED;
import edu.greenblitz.tobyDetermined.commands.LED.SetCubeLED;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.FullGripAndPutInClaw;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateByPower;
import edu.greenblitz.tobyDetermined.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.*;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveSelectedTargetLeft;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveSelectedTargetRight;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.EjectFromClaw;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToGrid;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.LED;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.BellyGameObjectSensor;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.RotatingBelly;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.tobyDetermined.commands.intake.extender.ExtendRoller;
import edu.greenblitz.tobyDetermined.commands.intake.extender.RetractRoller;
import edu.greenblitz.tobyDetermined.commands.intake.extender.ToggleRoller;
import edu.greenblitz.tobyDetermined.commands.swerve.ToggleBrakeCoast;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
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
		//Normal buttons.
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
		//Override buttons.
		StartEndCommand powerElbow = new StartEndCommand(() -> Elbow.getInstance().setMotorVoltage(Elbow.getStaticFeedForward(Extender.getInstance().getLength(), Elbow.getInstance().getAngleRadians())+ RobotMap.TelescopicArm.debugVoltage), () -> Elbow.getInstance().stop());
		StartEndCommand reversePowerElbow = new StartEndCommand(() -> Elbow.getInstance().setMotorVoltage(Elbow.getStaticFeedForward(Extender.getInstance().getLength(), Elbow.getInstance().getAngleRadians()) - RobotMap.TelescopicArm.debugVoltage), () -> Elbow.getInstance().stop());
		StartEndCommand powerExtender = new StartEndCommand(() -> Extender.getInstance().setMotorVoltage(Extender.getStaticFeedForward(Elbow.getInstance().getAngleRadians()) + RobotMap.TelescopicArm.debugVoltage), () -> Extender.getInstance().stop());
		StartEndCommand reversePowerExtender = new StartEndCommand(() -> Extender.getInstance().setMotorVoltage(Extender.getStaticFeedForward(Elbow.getInstance().getAngleRadians()) - RobotMap.TelescopicArm.debugVoltage), () -> Extender.getInstance().stop());
		secondJoystick.A.and(secondJoystick.L1).whileTrue(powerElbow);
		secondJoystick.B.and(secondJoystick.L1).whileTrue(reversePowerElbow);
		secondJoystick.Y.and(secondJoystick.L1).whileTrue(powerExtender);
		secondJoystick.X.and(secondJoystick.L1).whileTrue(reversePowerExtender);
		secondJoystick.POV_UP.and(secondJoystick.L1).onTrue(new InstantCommand(()-> RotatingBelly.getInstance().setObjectToCube()));
		secondJoystick.POV_DOWN.and(secondJoystick.L1).onTrue(new InstantCommand(()-> RotatingBelly.getInstance().setObjectToCone()));
		secondJoystick.POV_LEFT.and(secondJoystick.L1).whileTrue(new RotateByPower(RobotMap.RotatingBelly.ROTATING_POWER));
		secondJoystick.POV_RIGHT.and(secondJoystick.L1).whileTrue(new RotateByPower(-RobotMap.RotatingBelly.ROTATING_POWER));
		secondJoystick.R1.and(secondJoystick.L1).onTrue(new GripCone());
		secondJoystick.R3.and(secondJoystick.L1).onTrue(new GripCube());


	}

	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}

	public SmartJoystick getSecondJoystick() {
		return secondJoystick;
	}
}

