package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullGripCube extends SequentialCommandGroup {

	public FullGripCube(){
		super(
				new DisableClawCubeRotation().raceWith(
						new GoToPosition(RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_CUBE_POSITION)),
				new GripCube()
		);
	}

}
