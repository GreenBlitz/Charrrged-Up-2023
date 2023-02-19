package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.intake.extender.ExtendRoller;
import edu.greenblitz.tobyDetermined.commands.intake.extender.RetractRoller;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RunRoller;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateOutDoorDirection;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeRoller;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullGrip extends SequentialCommandGroup {

	public FullGrip() {
		//FIRST -> open the gripper and run it until an object is inside, along with to change the arm position
		addCommands(
				new ExtendRoller()
						.alongWith(new RunRoller())
						.alongWith(new RotateOutDoorDirection())
								.until(() -> IntakeRoller.getInstance().isObjectIn())
						.alongWith(new GoToPosition(RobotMap.TelescopicArm.PresetPositions.INTAKE_DROP_POSITION))
		);

		//throw the object
		addCommands(
				new RetractRoller()
		);
	}


}
