package edu.greenblitz.tobyDetermined.commands.SystemCheck;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.CloseIntakeAndAlign;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.GripFromBelly;
import edu.greenblitz.tobyDetermined.commands.intake.extender.RetractRoller;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateOutDoorDirection;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCube;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FullIntakeCube extends SequentialCommandGroup {
	public FullIntakeCube(){
		super(
				new RetractRoller(),
				new WaitCommand(0.5),
				new GripFromBelly()
		);
	}
}
