package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FromWallGoToPosition extends SequentialCommandGroup {

	public FromWallGoToPosition(double targetLengthInMeters, double targetAngleInRads){
		addCommands( //goes to the edge of the wall and then to the position
				new SimpleGoToPosition(targetLengthInMeters,targetAngleInRads),
				new SimpleGoToPosition(targetLengthInMeters,targetAngleInRads)
		);
	}
}
