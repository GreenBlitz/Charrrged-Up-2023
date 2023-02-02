package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class SelectTargetRight extends InstantCommand {
	public SelectTargetRight(){
		super(() -> Grid.getInstance().setPose(Grid.Location.values()[Grid.getInstance().getPose().ordinal() - 1]));
	}
}
