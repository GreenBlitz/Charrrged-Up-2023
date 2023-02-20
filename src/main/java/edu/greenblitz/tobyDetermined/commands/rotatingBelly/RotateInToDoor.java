package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RotateInToDoor extends ParallelDeadlineGroup {

    public RotateInToDoor(double waitTime){
        super(
                new WaitCommand(waitTime),
                new RotateInDoorDirection()
        );
    }

    public RotateInToDoor(){
        this(RobotMap.RotatingBelly.ROTATE_TO_DOOR_TIME);
    }

}
