package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.commands.NodeFullPathCommand;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.FullIntake;
import edu.greenblitz.tobyDetermined.commands.Auto.balance.LockWheels;
import edu.greenblitz.tobyDetermined.commands.Auto.balance.bangBangBalance.FullBalance;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.FullOpenIntake;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.GripFromBelly;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.GripFromFeeder;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RollByConst;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateByTrigger;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.bellyPusher.AutoDropCone;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.bellyPusher.PushCone;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.bellyPusher.RetractPusher;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.rotateAutomation.TimedAlignAfterDropCone;
import edu.greenblitz.tobyDetermined.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.tobyDetermined.commands.swerve.DriveSideWays;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.*;
import edu.greenblitz.tobyDetermined.commands.swerve.ResetVisionMoveToPose;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.RewritePresetPosition;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripBelly;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.ReleaseObject;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.ElbowMoveByJoysticks;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtenderMoveByJoysticks;

import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToGrid;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.ZigHail;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.rotation.RotatingBelly;
import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassis;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.greenblitz.utils.hid.SmartJoystick;
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
		Extender.getInstance().setDefaultCommand(new ExtenderMoveByJoysticks(getSecondJoystick()));
		//TO DO: make default command work
		Elbow.getInstance().setDefaultCommand(new ElbowMoveByJoysticks(getSecondJoystick()));
		secondJoystick.X.whileTrue(new NodeFullPathCommand(RobotMap.TelescopicArm.PresetPositions.ZIG_HAIL));
		secondJoystick.Y.whileTrue(new NodeFullPathCommand(RobotMap.TelescopicArm.PresetPositions.CONE_HIGH));
		secondJoystick.B.whileTrue(new NodeFullPathCommand(RobotMap.TelescopicArm.PresetPositions.LOW));
		secondJoystick.A.whileTrue(new NodeFullPathCommand(RobotMap.TelescopicArm.PresetPositions.REST_ABOVE_BELLY));
		secondJoystick.POV_UP.whileTrue(new NodeFullPathCommand(RobotMap.TelescopicArm.PresetPositions.POST_CONE_DROP));
		secondJoystick.POV_DOWN.whileTrue(new NodeFullPathCommand(
				ObjectSelector.IsCone() ? RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_CONE_POSITION : RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_CUBE_POSITION
		));
		secondJoystick.POV_LEFT.whileTrue(new NodeFullPathCommand(RobotMap.TelescopicArm.PresetPositions.CUBE_MID));

		secondJoystick.START.whileTrue(new InstantCommand(ObjectSelector::flipSelection));

		mainJoystick.X.whileTrue(new NodeFullTrack(RobotMap.TelescopicArm.PresetPositions.ZIG_HAIL));
		mainJoystick.Y.whileTrue(new NodeFullTrack(RobotMap.TelescopicArm.PresetPositions.CONE_HIGH));
		mainJoystick.B.whileTrue(new NodeFullTrack(RobotMap.TelescopicArm.PresetPositions.LOW));
		mainJoystick.A.whileTrue(new NodeFullTrack(RobotMap.TelescopicArm.PresetPositions.REST_ABOVE_BELLY));
		mainJoystick.POV_UP.whileTrue(new NodeFullTrack(RobotMap.TelescopicArm.PresetPositions.POST_CONE_DROP));
		mainJoystick.POV_DOWN.whileTrue(new NodeFullTrack(
				ObjectSelector.IsCone() ? RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_CONE_POSITION : RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_CUBE_POSITION
		));
		mainJoystick.POV_LEFT.whileTrue(new NodeFullTrack(RobotMap.TelescopicArm.PresetPositions.CUBE_MID));
	}
	


	private void initButtons() {
		butt();
	}

	public void romyButtons() {
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(false));
		mainJoystick.R1.whileTrue(new CombineJoystickMovement(false,true));//swerve fast mode
		mainJoystick.L1.and(mainJoystick.Y.negate()).whileTrue(new CombineJoystickMovement(true));  //slow mode
		mainJoystick.POV_LEFT.whileTrue(new DriveSideWays(DriveSideWays.Direction.LEFT, RobotMap.Swerve.Frankenstein.SIDEWAY_DRIVING_SPEED)); //left movement
		mainJoystick.POV_RIGHT.whileTrue(new DriveSideWays(DriveSideWays.Direction.RIGHT, RobotMap.Swerve.Frankenstein.SIDEWAY_DRIVING_SPEED)); //right movement
		mainJoystick.POV_UP.whileTrue(new FullBalance(true));
		mainJoystick.POV_DOWN.whileTrue(new FullBalance(false));
		mainJoystick.B.onTrue(new LockWheels()); //lock wheel

		// reset chassis pose (Y)
		mainJoystick.Y.and(mainJoystick.R1).onTrue(new InstantCommand(() -> SwerveChassis.getInstance().resetChassisPose())); //reset pose

		//reset by vision(for move to pose shit)
		mainJoystick.A.onTrue(new InstantCommand(() -> SwerveChassis.getInstance().resetToVision()));
		//amir and noam doing stuff
		mainJoystick.X.whileTrue(new ResetVisionMoveToPose());
	}

	public void amireeeButtons() {
		Extender.getInstance().setDefaultCommand(new ExtenderMoveByJoysticks(getSecondJoystick()));
 		Elbow.getInstance().setDefaultCommand(new ElbowMoveByJoysticks(getSecondJoystick()));

		//screenshot
		secondJoystick.R1.and(secondJoystick.L1).onTrue(new RewritePresetPosition());
		secondJoystick.B.and(secondJoystick.X).onTrue(new InstantCommand(() -> {
			Extender.getInstance().resetLength();
			Extender.getInstance().disableReverseLimit();
		}));
		//grid
		secondJoystick.POV_LEFT.onTrue(new MoveSelectedTargetLeft());
		secondJoystick.POV_RIGHT.onTrue(new MoveSelectedTargetRight());
		secondJoystick.POV_UP.onTrue(new MoveSelectedTargetUp());
		secondJoystick.POV_DOWN.onTrue(new MoveSelectedTargetDown());

		//score
		secondJoystick.Y.whileTrue(new GoToGrid());
		secondJoystick.B.and(secondJoystick.L1.negate()).and(secondJoystick.A.negate()).and(secondJoystick.X.negate()).and(secondJoystick.BACK.negate()).whileTrue(new ZigHail());
		secondJoystick.X.whileTrue(new ReleaseObject());
		secondJoystick.A.whileTrue(new GripFromFeeder());
		secondJoystick.A.and(secondJoystick.B).whileTrue(new GripBelly());

		//grab
		secondJoystick.START.whileTrue(new InstantCommand(ObjectSelector::flipSelection));
		secondJoystick.BACK.whileTrue(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.PRE_INTAKE_GRAB_POSITION));
		secondJoystick.L3.onTrue(new PushCone()).onFalse(new RetractPusher());
		secondJoystick.R3.onTrue(new AutoDropCone().andThen(new TimedAlignAfterDropCone()));
		secondJoystick.R1.and(secondJoystick.L1.negate()).whileTrue(new GripFromBelly());

		//intake and belly

		RotatingBelly.getInstance().setDefaultCommand(new RotateByTrigger(getSecondJoystick()));
		secondJoystick.L1.and(secondJoystick.R1.negate()).whileTrue(new FullOpenIntake().alongWith(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.PRE_INTAKE_GRAB_POSITION))).onFalse(new FullIntake());
		secondJoystick.L1.and(secondJoystick.B).whileTrue(new RollByConst(-1));

	}


	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}

	public SmartJoystick getSecondJoystick() {
		return secondJoystick;
	}

}

