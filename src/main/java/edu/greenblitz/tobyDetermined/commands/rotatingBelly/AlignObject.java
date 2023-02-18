package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AlignObject extends SequentialCommandGroup {
    // this RotateInToDoor knows the start location of the cone and therefore it needs less rotation time than usual
    public static final double SPECIAL_CASE_WAIT_TIME = 0.5;

    public AlignObject(){
        addCommands(new RotateOutOfDoor());

        addCommands(new RotateInToDoor(SPECIAL_CASE_WAIT_TIME));

    }
}
