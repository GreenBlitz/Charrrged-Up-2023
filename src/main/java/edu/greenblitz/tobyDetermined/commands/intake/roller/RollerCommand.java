package edu.greenblitz.tobyDetermined.commands.intake.roller;

import edu.greenblitz.tobyDetermined.commands.intake.IntakeCommand;

public abstract class RollerCommand extends IntakeCommand {
	public RollerCommand() {
		super();
		require(intake.getRoller());
	}
}
