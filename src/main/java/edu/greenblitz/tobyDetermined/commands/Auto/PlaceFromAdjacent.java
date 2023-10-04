package edu.greenblitz.tobyDetermined.commands.Auto;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PlaceFromAdjacent extends SequentialCommandGroup {
	public PlaceFromAdjacent(RobotMap.TelescopicArm.PresetPositions targetPosition) {
		super(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.ZIG_HAIL),
				new GoToPosition(targetPosition)
		);
	}
}
