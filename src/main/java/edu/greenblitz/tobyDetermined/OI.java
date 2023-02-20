package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.commands.swerve.*;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.*;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveSelectedTargetLeft;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveSelectedTargetRight;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.tobyDetermined.commands.intake.extender.ExtendRoller;
import edu.greenblitz.tobyDetermined.commands.intake.extender.RetractRoller;
import edu.greenblitz.tobyDetermined.commands.intake.extender.ToggleRoller;
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
		romyButtons();
	}

	public void romyButtons(){
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(false));
		mainJoystick.R1.whileTrue(new CombineJoystickMovement(true)); //slow mode
		mainJoystick.L1.onTrue(new MoveToGrid()); //move to pose
		mainJoystick.POV_UP.onTrue(new AdvancedBalanceOnRamp(true)); //ramp from community
		mainJoystick.POV_DOWN.onTrue(new AdvancedBalanceOnRamp(false)); //ramp form not community
		mainJoystick.POV_LEFT.onTrue(new DriveSidewaysUntilEdge(DriveSidewaysUntilEdge.Direction.LEFT,0.1)); //left movement
		mainJoystick.POV_RIGHT.onTrue(new DriveSidewaysUntilEdge(DriveSidewaysUntilEdge.Direction.RIGHT, 0.1)); //right movement
		mainJoystick.A.onTrue(new CancelChassisCommands()); //cancel auto command
		mainJoystick.B.onTrue(new LockWheels()); //lock wheel
		mainJoystick.START.toggleOnTrue(new InstantCommand()); //todo - toggle leg
	}

	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}

	public SmartJoystick getSecondJoystick() {
		return secondJoystick;
	}
}

