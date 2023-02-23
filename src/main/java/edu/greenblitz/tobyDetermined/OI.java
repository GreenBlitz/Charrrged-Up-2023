package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.*;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.*;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.math.util.Units;
import edu.greenblitz.tobyDetermined.commands.swerve.AdvancedBalanceOnRamp;
import edu.greenblitz.tobyDetermined.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.*;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveSelectedTargetLeft;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveSelectedTargetRight;
import edu.greenblitz.tobyDetermined.subsystems.Limelight.MultiLimelight;
import edu.greenblitz.tobyDetermined.commands.swerve.DriveSidewaysUntilEdge;
import edu.greenblitz.tobyDetermined.commands.swerve.LockWheels;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveToGrid;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.hid.SmartJoystick;
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
		mainJoystick.POV_UP.toggleOnTrue(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.CONE_HIGH));
		mainJoystick.POV_LEFT.toggleOnTrue(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.CONE_MID));
		mainJoystick.POV_RIGHT.toggleOnTrue(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.CUBE_HIGH));
		mainJoystick.POV_DOWN.toggleOnTrue(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.LOW));
        mainJoystick.BACK.toggleOnTrue(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_POSITION));

        mainJoystick.START.onTrue(new ResetExtender());
	
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
		mainJoystick.START.toggleOnTrue(new InstantCommand()); //todo - toggle leg
        mainJoystick.Y.onTrue(new InstantCommand(() -> SwerveChassis.getInstance().resetChassisPose())).and(() -> mainJoystick.R1.getAsBoolean()); //reset pose
    }



    public SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public SmartJoystick getSecondJoystick() {
        return secondJoystick;
    }
}

