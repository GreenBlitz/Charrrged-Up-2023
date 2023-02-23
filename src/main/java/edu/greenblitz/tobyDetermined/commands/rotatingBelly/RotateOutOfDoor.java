package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;

public class RotateOutOfDoor extends RotatingBellyCommand {


    @Override
    public void execute() {
        belly.setPower(-RobotMap.RotatingBelly.ROTATING_POWER);
    }

    @Override
    public boolean isFinished() {
        if (belly.isLimitSwitchPressed()) {
            Timer.delay(RobotMap.RotatingBelly.ROTATE_OUT_OF_DOOR_TIME);
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        belly.stop();
    }
}
