package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.GBCommand;


public abstract class SwerveCommand extends GBCommand {
	protected SwerveChassis swerve;
	
	public SwerveCommand() {
		swerve = SwerveChassis.getInstance();
		require(swerve);
	}
}
