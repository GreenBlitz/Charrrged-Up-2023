package edu.greenblitz.tobyDetermined.commands.intake.extender;

import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeExtender;
import edu.greenblitz.utils.GBCommand;

public abstract class ExtenderCommand extends GBCommand {
	IntakeExtender extender;

	public ExtenderCommand() {
		extender = IntakeExtender.getInstance();
		require(extender);
	}
}
