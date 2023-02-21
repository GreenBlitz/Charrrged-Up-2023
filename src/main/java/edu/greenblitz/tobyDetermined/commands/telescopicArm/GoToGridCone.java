package edu.greenblitz.tobyDetermined.commands.telescopicArm;

import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;

public class GoToGridCone extends GoToPosition{

	public GoToGridCone() {
		super(Grid.getInstance().getSelectedConePosition());
	}
}