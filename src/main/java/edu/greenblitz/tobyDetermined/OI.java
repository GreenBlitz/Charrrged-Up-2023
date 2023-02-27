package edu.greenblitz.tobyDetermined;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.*;
import edu.greenblitz.tobyDetermined.commands.swerve.balance.*;
import edu.greenblitz.tobyDetermined.commands.swerve.balance.bangBangBalance.BangBangBalance;
import edu.greenblitz.tobyDetermined.commands.swerve.balance.bangBangBalance.FullBalance;
import edu.greenblitz.tobyDetermined.commands.swerve.balance.bangBangBalance.GetOnRamp;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.RewritePresetPosition;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.EjectFromClaw;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.*;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.*;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToGrid;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.tobyDetermined.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.tobyDetermined.commands.swerve.DriveSidewaysUntilEdge;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
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
        romyButtons();
        Extender.getInstance().setDefaultCommand(new ExtenderMoveByJoysticks(getSecondJoystick()));
        Elbow.getInstance().setDefaultCommand(new elbowMoveByJoysticks(getSecondJoystick()));
        secondJoystick.R1.and(secondJoystick.L1).onTrue(new RewritePresetPosition());
//        mainJoystick.POV_LEFT.onTrue(new MoveSelectedTargetLeft());
//        mainJoystick.POV_RIGHT.onTrue(new MoveSelectedTargetRight());
//        mainJoystick.POV_UP.onTrue(new MoveSelectedTargetUp());
//        mainJoystick.POV_DOWN.onTrue(new MoveSelectedTargetDown());
        secondJoystick.A.whileTrue(new GoToGrid());
        mainJoystick.B.whileTrue(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_POSITION));
//        secondJoystick.X.whileTrue(new Grip());
        secondJoystick.X.onTrue(new InstantCommand(ObjectSelector::flipSelection));
        secondJoystick.Y.whileTrue(new EjectFromClaw());
        secondJoystick.START.whileTrue(new GripCone());
        secondJoystick.BACK.whileTrue(new GripCube());
        secondJoystick.X.and(secondJoystick.R1).onTrue(new ResetExtender());




    }

    public void romyButtons() {
        SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(false));
        mainJoystick.R1.whileTrue(new CombineJoystickMovement(true)); //slow mode
        mainJoystick.B.onTrue(new InstantCommand(()-> SwerveChassis.getInstance().resetToVision()));
        mainJoystick.L1.whileTrue(new MoveToGrid()); //move to pose
        mainJoystick.POV_UP.whileTrue(new FullBalance(1)); //ramp from community
        mainJoystick.POV_DOWN.whileTrue(new FullBalance(-1)); //ramp form not community
        mainJoystick.POV_LEFT.whileTrue(new DriveSidewaysUntilEdge(DriveSidewaysUntilEdge.Direction.LEFT, 0.5)); //left movement
        mainJoystick.POV_RIGHT.whileTrue(new DriveSidewaysUntilEdge(DriveSidewaysUntilEdge.Direction.RIGHT, 0.5)); //right movement
        mainJoystick.B.onTrue(new LockWheels()); //lock wheel
//		mainJoystick.START.toggleOnTrue(new InstantCommand()); //todo - toggle leg

        // reset chassis pose (Y)
        mainJoystick.Y.onTrue(new InstantCommand(() -> SwerveChassis.getInstance().resetChassisPose())).and(() -> mainJoystick.R1.getAsBoolean()); //reset pose
        // reset encoders by stick (X and R1)
        mainJoystick.X.and(mainJoystick.R1).onTrue(new InstantCommand(()-> SwerveChassis.getInstance().resetEncodersByCalibrationRod()));
        // while held rot motors on coast (X and L1)
        mainJoystick.X.and(mainJoystick.L1).whileTrue(new StartEndCommand(()->SwerveChassis.getInstance().setAngleMotorsIdleMode(CANSparkMax.IdleMode.kCoast), ()->SwerveChassis.getInstance().setAngleMotorsIdleMode(CANSparkMax.IdleMode.kBrake)));
    }




    public SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public SmartJoystick getSecondJoystick() {
        return secondJoystick;
    }
}

