package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AlignObject extends SequentialCommandGroup {
    public AlignObject(){
        addCommands(new RotateOutOfDoor());
        addCommands(new RotateInToDoor());

    }
}
