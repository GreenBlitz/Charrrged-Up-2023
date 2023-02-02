package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class SelectTargetLeft extends GBCommand {
	@Override
	public void initialize() {
		Grid.getInstance().setPose(Grid.Location.values()[Grid.getInstance().getPose().ordinal()-1]);
	}
	
	@Override
	public void execute() {
	
	}
}
