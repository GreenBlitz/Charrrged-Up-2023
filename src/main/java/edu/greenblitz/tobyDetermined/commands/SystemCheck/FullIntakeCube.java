package edu.greenblitz.tobyDetermined.commands.SystemCheck;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.CloseIntakeAndAlign;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.FullOpenIntake;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.GripFromBelly;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateOutDoorDirection;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.MoveSelectedTargetDown;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.DefaultRotateWhenCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.ReleaseObject;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToGrid;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.GoToPosition;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition.ZigHail;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FullIntakeCube extends SequentialCommandGroup {
	public FullIntakeCube(){
		super(
				new CloseIntakeAndAlign(),
				new RotateOutDoorDirection().raceWith(new WaitCommand(1)),
				new GripFromBelly()
		);
	}
}
