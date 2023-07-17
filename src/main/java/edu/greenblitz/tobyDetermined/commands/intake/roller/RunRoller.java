package edu.greenblitz.tobyDetermined.commands.intake.roller;

import edu.greenblitz.tobyDetermined.RobotMap;

public class RunRoller extends RollerCommand {
	@Override
	public void execute() {
		roller.roll(RobotMap.Intake.DEFAULT_POWER);
	}

	@Override
	public void end(boolean interrupted) {
		roller.stop();
	}
}
