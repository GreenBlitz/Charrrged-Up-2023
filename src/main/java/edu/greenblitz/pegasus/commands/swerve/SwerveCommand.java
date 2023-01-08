package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.pegasus.utils.commands.GBCommand;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;


public abstract class SwerveCommand extends GBCommand {
	protected SwerveChassis swerve;

	public SwerveCommand() {
		swerve = SwerveChassis.getInstance();
		require(swerve);
	}
}
