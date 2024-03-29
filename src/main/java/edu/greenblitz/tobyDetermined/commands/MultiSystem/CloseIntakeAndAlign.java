package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.greenblitz.tobyDetermined.commands.intake.extender.RetractRoller;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateOutDoorDirection;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.rotateAutomation.TimedAlignObject;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class CloseIntakeAndAlign extends ParallelCommandGroup {

    public CloseIntakeAndAlign() {
        super(
                new RetractRoller(),
                new WaitCommand(2).deadlineWith(new RotateOutDoorDirection())
                        .andThen(new TimedAlignObject())
        );
    }


}
