package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.*;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.RewritePresetPosition;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.EjectFromClaw;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.Grip;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.*;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.*;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToGrid;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.math.util.Units;
import edu.greenblitz.tobyDetermined.commands.swerve.AdvancedBalanceOnRamp;
import edu.greenblitz.tobyDetermined.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.tobyDetermined.commands.swerve.DriveSidewaysUntilEdge;
import edu.greenblitz.tobyDetermined.commands.swerve.LockWheels;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
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



	private void initButtons() {
        romyButtons();
        Extender.getInstance().setDefaultCommand(new ExtenderMoveByJoysticks(getSecondJoystick()));
        Elbow.getInstance().setDefaultCommand(new elbowMoveByJoysticks(getSecondJoystick()));
        secondJoystick.R1.and(secondJoystick.L1).onTrue(new RewritePresetPosition());
        secondJoystick.POV_LEFT.onTrue(new MoveSelectedTargetLeft());
        secondJoystick.POV_RIGHT.onTrue(new MoveSelectedTargetRight());
        secondJoystick.POV_UP.onTrue(new MoveSelectedTargetUp());
        secondJoystick.POV_DOWN.onTrue(new MoveSelectedTargetDown());
        secondJoystick.A.onTrue(new GoToGrid());
        secondJoystick.B.whileTrue(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.FEEDER));
        secondJoystick.X.whileTrue(new Grip());
        secondJoystick.Y.whileTrue(new EjectFromClaw());



    }

    public void romyButtons() {
        SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(true));
        mainJoystick.R1.whileTrue(new CombineJoystickMovement(true)); //slow mode
        mainJoystick.L1.whileTrue(new MoveToGrid()); //move to pose
        mainJoystick.POV_UP.whileTrue(new AdvancedBalanceOnRamp(true)); //ramp from community
        mainJoystick.POV_DOWN.whileTrue(new AdvancedBalanceOnRamp(false)); //ramp form not community
        mainJoystick.POV_LEFT.whileTrue(new DriveSidewaysUntilEdge(DriveSidewaysUntilEdge.Direction.LEFT, 0.5)); //left movement
        mainJoystick.POV_RIGHT.whileTrue(new DriveSidewaysUntilEdge(DriveSidewaysUntilEdge.Direction.RIGHT, 0.5)); //right movement
        mainJoystick.B.onTrue(new LockWheels()); //lock wheel
//		mainJoystick.START.toggleOnTrue(new InstantCommand()); //todo - toggle leg
        mainJoystick.Y.onTrue(new InstantCommand(() -> SwerveChassis.getInstance().resetChassisPose())).and(() -> mainJoystick.R1.getAsBoolean()); //reset pose
    }



    public SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public SmartJoystick getSecondJoystick() {
        return secondJoystick;
    }
}

