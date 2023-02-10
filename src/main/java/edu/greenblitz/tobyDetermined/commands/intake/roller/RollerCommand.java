package edu.greenblitz.tobyDetermined.commands.intake.roller;

import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeRoller;
import edu.greenblitz.utils.GBCommand;

public abstract class RollerCommand extends GBCommand {
	IntakeRoller roller;
	RollerCommand(){
		roller = IntakeRoller.getInstance();
		require(roller);
	}
}
