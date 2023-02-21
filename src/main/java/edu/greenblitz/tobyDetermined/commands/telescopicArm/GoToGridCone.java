package edu.greenblitz.tobyDetermined.commands.telescopicArm;

import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;

public class GoToGridCone extends GoToPosition {

	public GoToGridCone() {
		super(Grid.getInstance().getSelectedConePosition());
	}
}