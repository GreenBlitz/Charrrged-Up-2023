package edu.greenblitz.tobyDetermined.commands.rotatingBelly.rotateAutomation;

import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateInDoorDirection;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateOutDoorDirection;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TimedAlignObject extends SequentialCommandGroup {

	public TimedAlignObject(){
		addCommands(new RotateInDoorDirection().raceWith(new WaitCommand(0.5)), new RotateOutDoorDirection().raceWith(new WaitCommand(1.0)));
	}

}
