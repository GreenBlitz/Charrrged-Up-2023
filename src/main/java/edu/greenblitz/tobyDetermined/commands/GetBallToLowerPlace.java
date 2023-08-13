package edu.greenblitz.tobyDetermined.commands;


import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeRoller;
import edu.greenblitz.utils.GBCommand;

public class GetBallToLowerPlace extends GBCommand {
	
	
	protected IntakeRoller roller;
	
	public GetBallToLowerPlace() {
		roller = IntakeRoller.getInstance();
		require(roller);
	}
	
	@Override
	public void execute() {
		roller.roll(0.3);
	}
	
	@Override
	public void end(boolean interrupted) {
		roller.roll(0);
	}
}