package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class FullRotateToPlace extends SequentialCommandGroup {
    public FullRotateToPlace (){
        addCommands(new RotateOutOfDoor());
        addCommands(new RotateInToDoor());

    }
}
