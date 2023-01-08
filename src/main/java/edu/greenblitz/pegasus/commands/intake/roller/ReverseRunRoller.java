package edu.greenblitz.pegasus.commands.intake.roller;

public class ReverseRunRoller extends RollerCommand {
	@Override
	public void execute() {
		intake.moveRoller(true);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		intake.stopRoller();
	}
}
