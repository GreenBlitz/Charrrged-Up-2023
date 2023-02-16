package edu.greenblitz.tobyDetermined.commands.MultiSubSystemCommands;

import edu.greenblitz.tobyDetermined.commands.rotatingBelly.FullRotateToPlace;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripFromClaw;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import shapeless.ops.zipper;

public class GetObjectToClaw extends SequentialCommandGroup {

    public GetObjectToClaw(){
        addCommands(new FullRotateToPlace());
        addCommands(new GripFromClaw());
    }

}
