package edu.greenblitz.tobyDetermined.commands.Auto;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.DefaultRotateWhenCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.DropCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.StayAtCurrentAngle;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FullConeHigh extends SequentialCommandGroup {
	
	public FullConeHigh(){
		super(
				new PlaceFromAdjacent(RobotMap.TelescopicArm.PresetPositions.CONE_HIGH).raceWith(new WaitCommand(5)).alongWith(new GripCone().raceWith(new WaitCommand(1))),
				new WaitCommand(0.1).andThen(new DropCone().alongWith(new WaitCommand(0.2))).deadlineWith(new StayAtCurrentAngle())
		);
	}
}
