package edu.greenblitz.tobyDetermined.commands.intake.Roller;

public class StopRoller extends RollerCommand{
	@Override
	public void initialize() {
		roller.stop();
	}
}
