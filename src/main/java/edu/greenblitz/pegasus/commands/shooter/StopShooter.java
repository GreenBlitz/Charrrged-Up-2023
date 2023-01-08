package edu.greenblitz.pegasus.commands.shooter;

public class StopShooter extends ShooterCommand {
	@Override
	public void initialize() {
		shooter.setPower(0);
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
