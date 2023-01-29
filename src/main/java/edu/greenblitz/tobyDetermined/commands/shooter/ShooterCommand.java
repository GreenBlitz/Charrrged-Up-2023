package edu.greenblitz.tobyDetermined.commands.shooter;

import edu.greenblitz.tobyDetermined.subsystems.Shooter;
import edu.greenblitz.utils.GBCommand;


public abstract class ShooterCommand extends GBCommand {
	protected Shooter shooter;

	public ShooterCommand() {
		shooter = Shooter.getInstance();
		require(shooter);
	}
}
