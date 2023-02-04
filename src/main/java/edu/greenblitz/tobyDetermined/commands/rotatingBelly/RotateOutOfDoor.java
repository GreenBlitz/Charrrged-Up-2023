package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;

public class RotateOutOfDoor extends RotatingBellyCommand{

    private static final double endTimeSeconds = 1;
    @Override
    public void execute() {
        belly.setSpeed(RobotMap.RotatingBelly.PERCENTAGE_ROTATING_SPEED);
    }

    @Override
    public boolean isFinished() {
        Timer.delay(1);
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        belly.stop();
    }
}
