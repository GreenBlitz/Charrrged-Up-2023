package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;

public class RotateOutOfDoor extends RotatingBellyCommand{

    private static final double endTimeSeconds = 1;
    @Override
    public void execute() {
        belly.setSpeed(-RobotMap.RotatingBelly.PERCENTAGE_ROTATING_SPEED);
    }

    @Override
    public boolean isFinished() {
        if(belly.isMacroSwitchPressed()){
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
