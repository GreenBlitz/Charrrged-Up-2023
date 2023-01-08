package edu.greenblitz.pegasus.commands.intake.extender;

import edu.greenblitz.pegasus.commands.intake.IntakeCommand;

public abstract class ExtenderCommand extends IntakeCommand {
	public ExtenderCommand() {
		super();
		require(intake.getExtender());
	}
}
