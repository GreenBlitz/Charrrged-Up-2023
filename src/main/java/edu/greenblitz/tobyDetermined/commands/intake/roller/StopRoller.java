package edu.greenblitz.tobyDetermined.commands.intake.roller;

public class StopRoller extends RollerCommand {
	@Override
	public void initialize() {
		roller.stop();
	}
}
