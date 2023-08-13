package edu.greenblitz.tobyDetermined.commands;


import edu.greenblitz.tobyDetermined.subsystems.Funnel;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeRoller;
import edu.greenblitz.utils.GBCommand;

public class EvacuateFromRoller extends GBCommand {
	
	protected Funnel funnel;
	protected IntakeRoller roller;
	
	public EvacuateFromRoller() {
		funnel = Funnel.getInstance();
		roller = IntakeRoller.getInstance();
		require(funnel);
		require(roller);
	}
	
	@Override
	public void execute() {
		roller.roll(-0.8);
	}
	
	@Override
	public void end(boolean interrupted) {
		funnel.setPower(0);
		roller.roll(0);
	}
}