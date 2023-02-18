package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FullRotateToPlace extends SequentialCommandGroup {
    public FullRotateToPlace (){
        addCommands(new RotateOutOfDoor().deadlineWith(new WaitCommand(0)));
        addCommands(new RotateObjectToDoor());
    }
}
