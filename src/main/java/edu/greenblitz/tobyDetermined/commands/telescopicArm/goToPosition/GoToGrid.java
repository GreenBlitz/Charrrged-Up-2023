package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;

public class GoToGrid extends GoToPosition{

	public GoToGrid() {
		super(Grid.getInstance().getArmPositionByObject());
	}
}