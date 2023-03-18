package edu.greenblitz.tobyDetermined.commands.Auto;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.DropCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.ReleaseObject;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.StayAtCurrentAngle;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FullConeHighAndReturn extends SequentialCommandGroup {
	
	public FullConeHighAndReturn(){
		super(
				new WaitCommand(0.2),
				new PlaceFromAdjacent(RobotMap.TelescopicArm.PresetPositions.CONE_HIGH).raceWith(new WaitCommand(5)),
				new WaitCommand(0.1).andThen(new DropCone().alongWith(new WaitCommand(0.2))).deadlineWith(new StayAtCurrentAngle()),
				new PlaceFromAdjacent(RobotMap.TelescopicArm.PresetPositions.COMMUNITY_PRE_GRID).raceWith(new WaitCommand(5))
		);
	}
}
