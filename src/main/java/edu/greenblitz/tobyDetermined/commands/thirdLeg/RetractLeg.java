package edu.greenblitz.tobyDetermined.commands.thirdLeg;

public class RetractLeg extends ThirdLegCommand {
	@Override
	public void initialize() {
		extender.retract();
	}
	
	@Override
	public boolean isFinished() {
		return true;
	}
}
