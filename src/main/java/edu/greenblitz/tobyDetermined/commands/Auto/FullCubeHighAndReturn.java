package edu.greenblitz.tobyDetermined.commands.Auto;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.DefaultRotateWhenCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.DropCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.EjectCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.ReleaseObject;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.StayAtCurrentAngle;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FullCubeHighAndReturn extends SequentialCommandGroup {
	public FullCubeHighAndReturn(){
		super(
				new PlaceFromAdjacent(RobotMap.TelescopicArm.PresetPositions.CUBE_HIGH).raceWith(new WaitCommand(5)).deadlineWith(new DefaultRotateWhenCube()),

				new EjectCube().alongWith(new WaitCommand(0.2)).deadlineWith(new StayAtCurrentAngle()),
				new PlaceFromAdjacent(RobotMap.TelescopicArm.PresetPositions.COMMUNITY_PRE_GRID).raceWith(new WaitCommand(5))
		);
	}
}
