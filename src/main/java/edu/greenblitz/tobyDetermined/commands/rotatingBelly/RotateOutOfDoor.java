package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RotateOutOfDoor extends RotatingBellyCommand {

    private static final double endTimeSeconds = 0.5;

    @Override
    public void execute() {
        belly.setPower(-RobotMap.RotatingBelly.ROTATING_POWER);
    }

    @Override
    public boolean isFinished() {
        if (belly.isLimitSwitchPressed()) {
            Timer.delay(endTimeSeconds);
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        belly.stop();
    }
}
