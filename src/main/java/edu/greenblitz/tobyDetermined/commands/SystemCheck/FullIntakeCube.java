package edu.greenblitz.tobyDetermined.commands.SystemCheck;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.CloseIntakeAndAlign;
import edu.greenblitz.tobyDetermined.commands.MultiSystem.GripFromBelly;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateOutDoorDirection;
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
