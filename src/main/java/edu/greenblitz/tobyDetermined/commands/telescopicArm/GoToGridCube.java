package edu.greenblitz.tobyDetermined.commands.telescopicArm;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;

public class GoToGridCube extends GoToPosition {

	public GoToGridCube() {
		super(Grid.getInstance().getSelectedCubePosition());
	}
}