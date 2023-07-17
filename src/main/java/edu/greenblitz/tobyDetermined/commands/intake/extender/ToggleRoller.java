package edu.greenblitz.tobyDetermined.commands.intake.extender;

public class ToggleRoller extends ExtenderCommand {
	@Override
	public void initialize() {
		extender.toggleExtender();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
