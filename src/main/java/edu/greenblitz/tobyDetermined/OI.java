package edu.greenblitz.tobyDetermined;


import edu.greenblitz.tobyDetermined.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveToGrid;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.SetSelectedTargetLeft;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.SetSelectedTargetRight;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.DriverStation;
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
			instance = new OI();
		}
		return instance;
	}
	
	public static boolean isIsHandled() {
		return isHandled;
	}
	
	public static void disableHandling() {
		isHandled = false;
	}
	
	public double countB = 0;
	
	private void initButtons() {
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(true));
		mainJoystick.Y.onTrue(new InstantCommand(() -> SwerveChassis.getInstance().resetChassisPose()));
		mainJoystick.POV_RIGHT.onTrue(new SetSelectedTargetRight(DriverStation.Alliance.Blue));
		mainJoystick.POV_LEFT.onTrue(new SetSelectedTargetLeft(DriverStation.Alliance.Blue));
		mainJoystick.A.onTrue(new MoveToGrid());
//		mainJoystick.A.onTrue(new MoveToPos(new Pose2d()));
//		mainJoystick.B.onTrue(new InstantCommand(()->SwerveChassis.getInstance().poseEstimator.resetPosition(new Rotation2d(SwerveChassis.getInstance().getPigeonGyro().getYaw()), SwerveChassis.getInstance().getSwerveModulePositions(), new Pose2d(0,0, new Rotation2d(0,0)))));
	}
	
	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}
	
	public SmartJoystick getSecondJoystick() {
		return secondJoystick;
	}
	
}