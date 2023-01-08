package edu.greenblitz.pegasus.commands.intake.extender;

public class ExtendRoller extends ExtenderCommand {
	@Override
	public void initialize() {
		intake.extend();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
