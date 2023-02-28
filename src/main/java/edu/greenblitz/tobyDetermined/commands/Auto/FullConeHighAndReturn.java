package edu.greenblitz.tobyDetermined.commands.Auto;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCone;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullConeHighAndReturn extends SequentialCommandGroup {
	
	public FullConeHighAndReturn(){
		super(
				new PlaceFromAdjacent(RobotMap.TelescopicArm.PresetPositions.CONE_HIGH),
				new GripCone(),
				new PlaceFromAdjacent(RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_POSITION)
		);
	}
}
