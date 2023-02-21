package edu.greenblitz.tobyDetermined.commands.thirdLeg;

public class ToggleLeg extends ThirdLegCommand {
	@Override
	public void initialize() {
		extender.toggleExtender();
	}
	
	@Override
	public boolean isFinished() {
		return true;
	}
}
