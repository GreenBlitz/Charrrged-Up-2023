package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.pegasus.utils.commands.GBCommand;
import edu.greenblitz.pegasus.subsystems.Shooter;


public abstract class ShooterCommand extends GBCommand {
	protected Shooter shooter;

	public ShooterCommand() {
		shooter = Shooter.getInstance();
		require(shooter);
	}
}
