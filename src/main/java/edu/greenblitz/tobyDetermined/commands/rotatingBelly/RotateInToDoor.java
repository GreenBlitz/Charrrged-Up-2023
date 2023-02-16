package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RotateInToDoor extends ParallelRaceGroup {

    public RotateInToDoor(){
        addCommands(new RotateInDoorDirection());
        addCommands(new WaitCommand(RobotMap.RotatingBelly.ROTATE_TO_DOOR_TIME));
    }

}
