package edu.greenblitz.tobyDetermined.commands.multiSystem;

import edu.greenblitz.tobyDetermined.commands.intake.roller.ReverseRunRoller;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class EjectEnemyBallFromGripper extends ParallelRaceGroup { //todo can be deadline
	public EjectEnemyBallFromGripper() {
		addCommands(
				new WaitCommand(1.5),
				new ReverseRunRoller()
		);
	}
}
