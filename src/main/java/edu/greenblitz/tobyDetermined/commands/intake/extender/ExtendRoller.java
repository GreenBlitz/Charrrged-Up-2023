package edu.greenblitz.tobyDetermined.commands.intake.extender;

import edu.greenblitz.utils.GBCommand;

public class ExtendRoller extends ExtenderCommand {
	@Override
	public void initialize() {
		extender.extend();
	}
	
	@Override
	public boolean isFinished() {
		return true;
	}
}
