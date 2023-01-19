package edu.greenblitz.tobyDetermined;


import edu.greenblitz.tobyDetermined.commands.Auto.PathFollowerBuilder;
import edu.greenblitz.tobyDetermined.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
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

	private void initButtons() {
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(false));

		mainJoystick.Y.onTrue(new InstantCommand(() -> SwerveChassis.getInstance().resetChassisPose()));
		mainJoystick.POV_UP.onTrue(new InstantCommand(() -> SwerveChassis.getInstance().resetEncodersByCalibrationRod()));
		
		mainJoystick.X.onTrue(PathFollowerBuilder.getInstance().followPath("1 broder"));
		mainJoystick.A.onTrue(new InstantCommand(
				() -> SwerveChassis.getInstance().resetChassisPose(
						new Pose2d(new Translation2d(4, 4), new Rotation2d())
				)
		));

		mainJoystick.B.onTrue(new InstantCommand(){
			@Override
			public void initialize() {
				SwerveChassis.getInstance().resetAllEncoders();
			}
		});
	}

	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}

	public SmartJoystick getSecondJoystick() {
		return secondJoystick;
	}

}