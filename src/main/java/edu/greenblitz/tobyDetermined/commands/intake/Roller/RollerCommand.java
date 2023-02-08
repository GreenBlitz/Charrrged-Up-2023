package edu.greenblitz.tobyDetermined.commands.intake.Roller;

import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeRoller;
import edu.greenblitz.utils.GBCommand;

public abstract class RollerCommand extends GBCommand {
	IntakeRoller roller;
	RollerCommand(){
		super();
		roller = IntakeRoller.getInstance();
		require(roller);
	}
}
