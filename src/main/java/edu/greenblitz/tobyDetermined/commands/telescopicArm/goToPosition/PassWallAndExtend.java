package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.RotateToAngleRadians;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.StayAtCurrentAngle;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtendToLength;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.StayAtCurrentLength;
import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class PassWallAndExtend extends ParallelCommandGroup {

	public PassWallAndExtend(double lengthInMeters, double angleInRads) {
		addCommands(
				new RotateToAngleRadians(angleInRads) {
					@Override
					public boolean isFinished() {
						return elbow.isAtAngle(angleInRads) && Extender.getInstance().isAtLength(lengthInMeters);
					}
				},
				new ExtendToLength(RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE){
					@Override
					public boolean isFinished() {
						return Elbow.getInstance().getState() == Elbow.ElbowState.WALL_ZONE;
					}
				}
						.andThen(new ExtendToLength(lengthInMeters){
							@Override
							public boolean isFinished() {
								return extender.isAtLength(lengthInMeters);
							}
						}).andThen(new StayAtCurrentLength().until(() -> Elbow.getInstance().isAtAngle(angleInRads)))

		);
	}
}
