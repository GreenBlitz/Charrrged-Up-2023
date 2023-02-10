package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RotateOutOfDoor extends ParallelDeadlineGroup {

    public RotateOutOfDoor() {
        super(
                new WaitCommand(1),
                new RotateByPower(-RobotMap.RotatingBelly.ROTATING_POWER)
        );
    }
}
