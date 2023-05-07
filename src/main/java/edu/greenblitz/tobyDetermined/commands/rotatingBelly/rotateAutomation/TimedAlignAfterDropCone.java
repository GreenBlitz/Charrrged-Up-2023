package edu.greenblitz.tobyDetermined.commands.rotatingBelly.rotateAutomation;

import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateInDoorDirection;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateOutDoorDirection;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TimedAlignAfterDropCone extends SequentialCommandGroup {

	public TimedAlignAfterDropCone(){
		addCommands(new RotateInDoorDirection().raceWith(new WaitCommand(1.25)), new RotateOutDoorDirection().raceWith(new WaitCommand(1.0)));
	}

}
