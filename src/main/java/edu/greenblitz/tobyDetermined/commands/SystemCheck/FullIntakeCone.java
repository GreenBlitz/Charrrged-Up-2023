package edu.greenblitz.tobyDetermined.commands.SystemCheck;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.CloseIntakeAndAlign;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.GripFromBelly;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateOutDoorDirection;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCube;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FullIntakeCone extends SequentialCommandGroup {
	public FullIntakeCone(){
		super(
				new CloseIntakeAndAlign(),
				new GripFromBelly()
		);
	}
}
