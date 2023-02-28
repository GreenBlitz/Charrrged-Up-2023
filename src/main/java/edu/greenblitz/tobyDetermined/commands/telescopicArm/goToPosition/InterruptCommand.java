package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.GBCommand;

public class InterruptCommand extends GBCommand {
	public InterruptCommand(GBSubsystem ... subsystems){
		for (GBSubsystem subsystem:subsystems) {
			require(subsystem);
		}
	}
	
	@Override
	public boolean isFinished() {
		return true;
	}
}
