package edu.greenblitz.tobyDetermined.commands.intake.roller;

public class StopRoller extends RollerCommand {
	@Override
	public void initialize() {
		intake.stopRoller();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
