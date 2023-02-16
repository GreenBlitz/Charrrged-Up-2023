package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class RotateOutOfDoor extends ParallelRaceGroup {

    public RotateOutOfDoor(){
        addCommands(new RotateOutDoorDirection());
        addCommands(new WaitUntilCommand(()-> RotatingBelly.getInstance().isLimitSwitchPressed()).andThen(new WaitCommand(RobotMap.RotatingBelly.ROTATE_OUT_OF_DOOR_TIME)));
    }

}
