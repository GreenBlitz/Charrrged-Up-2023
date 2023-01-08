package edu.greenblitz.pegasus.commands.intake.roller;

import edu.greenblitz.pegasus.subsystems.Intake;

public class RunRoller extends RollerCommand {
	@Override
	public void execute() {
		intake.moveRoller(1);
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
