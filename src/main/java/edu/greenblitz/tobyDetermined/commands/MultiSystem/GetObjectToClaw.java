package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.AlignObject;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCone;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCube;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.RotatingBelly;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class GetObjectToClaw extends SequentialCommandGroup {

	/**
	 * rotates the object to the right place and grip with claw
	 */
	public GetObjectToClaw() {
		//move the arm to grabbing position and rotate the belly and grub the object
		addCommands(
				new AlignObject().unless(ObjectSelector::IsCube)
						.alongWith(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.PRE_INTAKE_GRAB_POSITION))
		);

		addCommands(new ConditionalCommand(new GripCone(), new GripCube(), ObjectSelector::IsCone));

	}

}
