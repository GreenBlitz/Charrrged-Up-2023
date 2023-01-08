package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromGripper;
import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.commands.shooter.ShooterEvacuate;
import edu.greenblitz.pegasus.commands.shooter.StopShooter;
import edu.greenblitz.pegasus.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.pegasus.commands.swerve.MoveByVisionSupplier;
import edu.greenblitz.pegasus.subsystems.Funnel;
import edu.greenblitz.pegasus.subsystems.Intake;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;

public class OI { //GEVALD

	private static OI instance;
	private static boolean isHandled = true;
	private final SmartJoystick mainJoystick;

	private final SmartJoystick secondJoystick;


	private OI() {
		mainJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.MAIN, 0.1);
		secondJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.SECOND, 0.2);
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

		mainJoystick.Y.whenPressed(new InstantCommand(() -> SwerveChassis.getInstance().resetChassisAngle()));
		mainJoystick.X.whenHeld(new MoveByVisionSupplier(false));
		mainJoystick.POV_UP.whenPressed(new InstantCommand(() -> SwerveChassis.getInstance().resetAllEncoders()));

		mainJoystick.R1.whenHeld(new StartEndCommand(() -> Intake.getInstance().getExtender().retract(),
				() -> Intake.getInstance().getExtender().extend()));

		secondJoystick.Y.whenHeld(new EjectEnemyBallFromGripper());

		secondJoystick.R1.whenHeld(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.RPM).andThen(new StopShooter()));

		secondJoystick.A.whenHeld(new InsertIntoShooter());

		secondJoystick.B.whenHeld(new RunRoller().alongWith(new RunFunnel().until(() -> Funnel.getInstance().isMacroSwitchPressed())));


		secondJoystick.START.toggleWhenPressed(new ToggleRoller());
		secondJoystick.POV_DOWN.whileHeld(new RunFunnel());
		secondJoystick.POV_UP.whenPressed(new ShooterEvacuate());

	}

	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}

	public SmartJoystick getSecondJoystick() {
		return secondJoystick;
	}

}