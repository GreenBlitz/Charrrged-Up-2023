package edu.greenblitz.tobyDetermined.commands.intake;

import edu.greenblitz.tobyDetermined.subsystems.Intake;
import edu.greenblitz.utils.GBCommand;

public abstract class IntakeCommand extends GBCommand {
	protected Intake intake;

	public IntakeCommand() {
		intake = Intake.getInstance();
	}
}
