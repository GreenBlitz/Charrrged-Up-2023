package edu.greenblitz.pegasus.commands.intake.roller;

import edu.greenblitz.pegasus.commands.intake.IntakeCommand;

public abstract class RollerCommand extends IntakeCommand {
	public RollerCommand() {
		super();
		require(intake.getRoller());
	}
}
