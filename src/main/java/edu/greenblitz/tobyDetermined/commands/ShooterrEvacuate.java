package edu.greenblitz.tobyDetermined.commands;


import edu.greenblitz.tobyDetermined.subsystems.Funnel;
import edu.greenblitz.tobyDetermined.subsystems.Shooterr;
import edu.greenblitz.tobyDetermined.subsystems.ThingsToHandleBalls;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeRoller;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ShooterrEvacuate extends GBCommand {
	private Shooterr shooterr;
	private Funnel funnel;
	private IntakeRoller roller;
	
	private ThingsToHandleBalls thingsToHandleBalls;
	
	public ShooterrEvacuate() {
		funnel = Funnel.getInstance();
		roller = IntakeRoller.getInstance();
		shooterr = Shooterr.getInstance();
		thingsToHandleBalls = ThingsToHandleBalls.getInstance();
		require(funnel);
		require(shooterr);
		require(roller);
		require(thingsToHandleBalls);
	}
	
	@Override
	public void execute() {
		funnel.setPower(0.8);
		roller.roll(0.3);
		shooterr.setPower(0.4590);
	}
	
	//changed
	@Override
	public boolean isFinished() {
		return false;
	}
	
	@Override
	public void end(boolean interrupted) {

		funnel.setPower(0);
		roller.roll(0);
		shooterr.setPower(0);
	}
}