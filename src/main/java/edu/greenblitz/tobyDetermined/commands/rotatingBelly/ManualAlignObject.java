package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class ManualAlignObject extends SequentialCommandGroup {

	public ManualAlignObject(){
		addCommands(new RotateOutDoorDirection().raceWith(new WaitCommand(2)), new RotateInDoorDirection().raceWith(new WaitCommand(2)));
	}

}
