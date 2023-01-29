package edu.greenblitz.tobyDetermined.commands.intake.extender;

import edu.greenblitz.tobyDetermined.commands.intake.IntakeCommand;

public abstract class ExtenderCommand extends IntakeCommand {
	public ExtenderCommand() {
		super();
		require(intake.getExtender());
	}
}
