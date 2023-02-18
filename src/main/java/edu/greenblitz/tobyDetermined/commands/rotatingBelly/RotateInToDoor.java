package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RotateInToDoor extends ParallelDeadlineGroup {

    public RotateInToDoor(){
        super(
                new WaitCommand(RobotMap.RotatingBelly.ROTATE_TO_DOOR_TIME),
                new RotateInDoorDirection()
        );
    }

}
