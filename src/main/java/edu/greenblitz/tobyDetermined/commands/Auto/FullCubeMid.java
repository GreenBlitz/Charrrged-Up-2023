package edu.greenblitz.tobyDetermined.commands.Auto;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.DefaultRotateWhenCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.EjectCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.StayAtCurrentAngle;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FullCubeMid extends SequentialCommandGroup {
	public FullCubeMid() {
		super(
				new GoToPosition(RobotMap.TelescopicArm.PresetPositions.CUBE_MID)
						.raceWith(new WaitCommand(5))
						.deadlineWith(new DefaultRotateWhenCube()),

				new EjectCube()
						.alongWith(new WaitCommand(0.2))
						.deadlineWith(new StayAtCurrentAngle())
		);
	}
}
