package edu.greenblitz.tobyDetermined.commands.Auto;

import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PlaceFromAdjacent extends SequentialCommandGroup {
	public PlaceFromAdjacent(Grid.Height height){
		super(new GoToPosition());
	}
}
