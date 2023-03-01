package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.RotateToAngleRadians;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtendToLength;
import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class PassWallAndExtend extends ParallelCommandGroup {

	public PassWallAndExtend(double lengthInMeters, double angleInRads) {
		addCommands(
				new RotateToAngleRadians(angleInRads),
				new ExtendToLength(0).until(() -> Extender.getInstance().getState() == Extender.ExtenderState.IN_WALL_LENGTH)
						.andThen(new WaitUntilCommand(() -> Elbow.getInstance().getState() == Elbow.ElbowState.WALL_ZONE))
						.andThen(new ExtendToLength(lengthInMeters))

		);
	}
}
