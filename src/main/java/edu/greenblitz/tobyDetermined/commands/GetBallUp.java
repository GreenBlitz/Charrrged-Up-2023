package edu.greenblitz.tobyDetermined.commands;



import edu.greenblitz.tobyDetermined.subsystems.Funnel;
import edu.greenblitz.tobyDetermined.subsystems.ThingsToHandleBalls;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeRoller;
import edu.greenblitz.utils.GBCommand;

import static scala.Predef.require;

public class GetBallUp extends GBCommand {
	
	protected Funnel funnel;
	protected IntakeRoller roller;
	protected ThingsToHandleBalls thingsToHandleBalls;
	
	public GetBallUp() {
		funnel = Funnel.getInstance();
		roller = IntakeRoller.getInstance();
		thingsToHandleBalls = ThingsToHandleBalls.getInstance();
		require(funnel);
		require(roller);
		require(thingsToHandleBalls);
	}
	
	@Override
	public void execute() {
		funnel.setPower(0.5);
		roller.roll(0.3);
	}
	
	//change remember
	@Override
	public boolean isFinished() {
		return thingsToHandleBalls.getSwitch();
	}
	
	@Override
	public void end(boolean interrupted) {
		funnel.setPower(0);
		roller.roll(0);
	}
}
