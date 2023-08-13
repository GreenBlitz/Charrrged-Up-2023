package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.Shooterr;
import edu.greenblitz.tobyDetermined.subsystems.ThingsToHandleBalls;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Shooting extends GBCommand {
	private Shooterr shooterr;
	private ThingsToHandleBalls thingsToHandleBalls;
	
	public Shooting(){
		this.shooterr = Shooterr.getInstance();
		this.thingsToHandleBalls = ThingsToHandleBalls.getInstance();
		require(shooterr);
	}
	
	@Override
	public void execute() {
		shooterr.setPower(0.4);
		new SequentialCommandGroup(
				new WaitCommand(0.5),
				new PrepareForShooter()///////change!!!!!
		).schedule();

	}
	
	@Override
	public boolean isFinished() {
		if(!thingsToHandleBalls.getSwitch()) {
			return true;
		}
		return false;
	}
	
	@Override
	public void end(boolean interrupted) {

		shooterr.setPower(0);
	}
}