package edu.greenblitz.tobyDetermined.commands.Auto;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FullConeHighAndReturn extends SequentialCommandGroup {

	public FullConeHighAndReturn() {
		super(
				new FullConeHigh(),
				new PlaceFromAdjacent(RobotMap.TelescopicArm.PresetPositions.COMMUNITY_PRE_GRID)
						.raceWith(new WaitCommand(5))
		);
	}
}
