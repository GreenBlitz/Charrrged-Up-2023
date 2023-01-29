package edu.greenblitz.tobyDetermined.commands.shooter;

import edu.greenblitz.tobyDetermined.subsystems.Shooter;

public class FlipShooter extends ShooterCommand {

	public FlipShooter() {}

	@Override
	public void initialize() {
		super.initialize();
		Shooter.getInstance().flip();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
