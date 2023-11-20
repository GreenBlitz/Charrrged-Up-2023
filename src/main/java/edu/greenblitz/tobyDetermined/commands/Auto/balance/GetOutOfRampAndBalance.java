package edu.greenblitz.tobyDetermined.commands.Auto.balance;

import edu.greenblitz.tobyDetermined.commands.Auto.balance.bangBangBalance.GetOnRamp;
import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassis;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class GetOutOfRampAndBalance extends SequentialCommandGroup {


	public static final double FORWARD_SPEED = -1; // m/s
	public static final double FORWARD_SPEED_ON_RAMP = -0.5; // m/s
	public static final double RAMP_ANGLE_DOWN = Units.degreesToRadians(5); // Rad
	public static final double OFF_RAMP_ANGLE = Units.degreesToRadians(5); // Rad

	public GetOutOfRampAndBalance() {
		super(
				new GetOnRamp(FORWARD_SPEED),
				new InstantCommand(
						()-> SwerveChassis.getInstance().moveByChassisSpeeds(
								FORWARD_SPEED_ON_RAMP,
								0,
								0,
								SwerveChassis.getInstance().getChassisAngle()
						)
				).until(() -> SwerveChassis.getInstance().getGyro().getRoll() < 0),
				new InstantCommand(
						()-> SwerveChassis.getInstance().moveByChassisSpeeds(
								FORWARD_SPEED,
								0,
								0,
								SwerveChassis.getInstance().getChassisAngle()
						)
				).raceWith(new WaitCommand(4)),
				new AdvancedBalanceOnRamp(false)

		);

	}


}
