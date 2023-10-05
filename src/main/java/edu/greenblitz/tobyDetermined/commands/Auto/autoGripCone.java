package edu.greenblitz.tobyDetermined.commands.Auto;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.DropCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.ZigHail;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class autoGripCone extends SequentialCommandGroup {

	public autoGripCone(){
		super(
			new SequentialCommandGroup(
					new PlaceFromAdjacent(RobotMap.TelescopicArm.PresetPositions.CONE_HIGH),
					new DropCone(),
					new ZigHail()
			).raceWith(new WaitCommand(5)),
				new DropCone()
		);
	}

}
