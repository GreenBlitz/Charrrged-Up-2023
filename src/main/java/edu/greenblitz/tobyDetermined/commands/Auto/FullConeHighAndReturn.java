package edu.greenblitz.tobyDetermined.commands.Auto;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.DropCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.StayAtCurrentAngle;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FullConeHighAndReturn extends SequentialCommandGroup {
	
	public FullConeHighAndReturn(){
		super(
				new PlaceFromAdjacent(RobotMap.TelescopicArm.PresetPositions.CONE_HIGH).raceWith(new WaitCommand(5)),
				new DropCone().deadlineWith(new StayAtCurrentAngle()),
				new PlaceFromAdjacent(RobotMap.TelescopicArm.PresetPositions.PRE_INTAKE_GRAB_POSITION).raceWith(new WaitCommand(5))
		);
	}
}
