package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class ManualAlignObject extends SequentialCommandGroup {

	public ManualAlignObject(){
		addCommands(new RotateInDoorDirection().raceWith(new WaitCommand(0.5)), new RotateOutDoorDirection().raceWith(new WaitCommand(1.0)));
	}

}
