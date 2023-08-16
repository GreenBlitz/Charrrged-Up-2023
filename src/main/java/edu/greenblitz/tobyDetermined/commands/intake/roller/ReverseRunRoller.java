package edu.greenblitz.tobyDetermined.commands.intake.roller;

public class ReverseRunRoller extends RollerCommand{
	@Override
	public void execute() {
		roller.setInverted(false);
		roller.rollOut();

	}
	
	@Override
	public void end(boolean interrupted) {
		roller.stop();
		roller.setInverted(true);
	}
}
