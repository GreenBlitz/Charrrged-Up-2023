package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullRotateToPlace extends SequentialCommandGroup {
    public FullRotateToPlace (){
        addCommands(new RotateOutOfDoor());
        addCommands(new RotateBellyObjectToPosition());
    }
}
