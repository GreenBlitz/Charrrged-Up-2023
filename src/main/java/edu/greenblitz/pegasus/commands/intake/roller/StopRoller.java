package edu.greenblitz.pegasus.commands.intake.roller;

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
