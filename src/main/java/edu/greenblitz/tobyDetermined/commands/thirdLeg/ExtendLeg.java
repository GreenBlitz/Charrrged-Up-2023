package edu.greenblitz.tobyDetermined.commands.thirdLeg;

public class ExtendLeg extends ThirdLegCommand {
	@Override
	public void initialize() {
		extender.extend();
	}
	
	@Override
	public boolean isFinished() {
		return true;
	}
}
