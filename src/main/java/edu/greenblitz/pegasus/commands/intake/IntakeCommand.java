package edu.greenblitz.pegasus.commands.intake;

import edu.greenblitz.pegasus.utils.commands.GBCommand;
import edu.greenblitz.pegasus.subsystems.Intake;

public abstract class IntakeCommand extends GBCommand {
	protected Intake intake;

	public IntakeCommand() {
		intake = Intake.getInstance();
	}
}
