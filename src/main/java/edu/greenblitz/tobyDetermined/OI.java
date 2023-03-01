package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.commands.MultiSystem.*;
import edu.greenblitz.tobyDetermined.commands.intake.extender.ExtendRoller;
import edu.greenblitz.tobyDetermined.commands.intake.extender.RetractRoller;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RollByConst;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.*;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.*;
import edu.greenblitz.tobyDetermined.commands.swerve.balance.LockWheels;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.RewritePresetPosition;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.*;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.*;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.*;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.ZigHail;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.RotatingBelly;
import edu.greenblitz.tobyDetermined.commands.Auto.PlaceFromAdjacent;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateByPower;
import edu.greenblitz.tobyDetermined.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.tobyDetermined.commands.swerve.DriveSidewaysUntilEdge;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.*;
import edu.greenblitz.tobyDetermined.commands.swerve.balance.LockWheels;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.RewritePresetPosition;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.elbowMoveByJoysticks;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtenderMoveByJoysticks;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToGrid;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.ZigHail;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import java.lang.annotation.Retention;

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
		romyButtons();
		amireeeButtons();
	}
	
	public void romyButtons() {
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(false));
		mainJoystick.R1.whileTrue(new CombineJoystickMovement(true)); //slow mode
		mainJoystick.L1.and(mainJoystick.Y.negate()).whileTrue(new MoveToGrid()); //move to pose
		mainJoystick.POV_LEFT.whileTrue(new DriveSidewaysUntilEdge(DriveSidewaysUntilEdge.Direction.LEFT, 0.5)); //left movement
		mainJoystick.POV_RIGHT.whileTrue(new DriveSidewaysUntilEdge(DriveSidewaysUntilEdge.Direction.RIGHT, 0.5)); //right movement
		mainJoystick.B.onTrue(new LockWheels()); //lock wheel
//		mainJoystick.START.toggleOnTrue(new InstantCommand()); //todo - toggle leg

        // reset chassis pose (Y)
        mainJoystick.Y.and(mainJoystick.L1).onTrue(new InstantCommand(() -> SwerveChassis.getInstance().resetToVision())); //reset pose
    }

    public void amireeeButtons(){
        Extender.getInstance().setDefaultCommand(new ExtenderMoveByJoysticks(getSecondJoystick()));
        Elbow.getInstance().setDefaultCommand(new elbowMoveByJoysticks(getSecondJoystick()));

        //screenshot
        secondJoystick.R1.and(secondJoystick.L1).onTrue(new RewritePresetPosition());

        //grid
        secondJoystick.POV_LEFT.onTrue(new MoveSelectedTargetLeft());
        secondJoystick.POV_RIGHT.onTrue(new MoveSelectedTargetRight());
        secondJoystick.POV_UP.onTrue(new MoveSelectedTargetUp());
        secondJoystick.POV_DOWN.onTrue(new MoveSelectedTargetDown());

        //score

        secondJoystick.Y.whileTrue(new GoToGrid());
        secondJoystick.B.and(secondJoystick.L1.negate()).and(secondJoystick.A.negate()).whileTrue(new ZigHail());
        secondJoystick.X.whileTrue(new ReleaseObject());
        secondJoystick.A.whileTrue(new GripFromFeeder());
		secondJoystick.A.and(secondJoystick.B).whileTrue(new GripBelly());

        //grab

        secondJoystick.START.whileTrue(new InstantCommand(ObjectSelector::flipSelection));
        secondJoystick.BACK.whileTrue(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.PRE_INTAKE_GRAB_POSITION));
        secondJoystick.R1.and(secondJoystick.L1.negate()).whileTrue(new GripFromBelly());
        //intake and belly

        RotatingBelly.getInstance().setDefaultCommand(new RotateByTrigger(getSecondJoystick()));
        secondJoystick.L1.and(secondJoystick.R1.negate()).whileTrue(new FullOpenIntake());
        secondJoystick.L1.onFalse(new CloseIntakeAndAlign());
		secondJoystick.L1.and(secondJoystick.B).whileTrue(new RollByConst(-1));
    }




    public SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public SmartJoystick getSecondJoystick() {
        return secondJoystick;
    }

}

