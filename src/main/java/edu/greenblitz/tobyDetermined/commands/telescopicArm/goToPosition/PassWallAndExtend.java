package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.RotateToAngleRadians;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.StayAtCurrentAngle;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtendToLength;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PassWallAndExtend extends SequentialCommandGroup {

	public PassWallAndExtend(double lengthInMeters, double angleInRads) {
		addCommands(
				new ExtendToLength(RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE).deadlineWith(new StayAtCurrentAngle()),
				new RotateToAngleRadians(angleInRads).deadlineWith(new ExtendToLength(RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE){
					@Override
					public boolean isFinished() {
						return false;
					}
				}),
				new ExtendToLength(lengthInMeters){
					@Override
					public boolean isFinished() {
						return extender.isAtLength(lengthInMeters);
					}
				}.deadlineWith(new RotateToAngleRadians(angleInRads){
					@Override
					public boolean isFinished() {
						return false;
					}
				})
				

/*
				new RotateToAngleRadians(angleInRads) {
					@Override
					public boolean isFinished() {
						return elbow.isAtAngle(angleInRads) && Extender.getInstance().isAtLength(lengthInMeters);
					}
				}.beforeStarting(new WaitUntilCommand(() -> Extender.getInstance().getState() == Extender.ExtenderState.IN_WALL_LENGTH)),
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
*/

		);
	}
}
