package edu.greenblitz.tobyDetermined.commands.intake.extender;

public class ToggleRoller extends ExtenderCommand {
	@Override
	public void initialize() {
		intake.extend();
	}
	
	@Override
	public void end(boolean interrupted) {
		intake.retract();
	}
	
}
