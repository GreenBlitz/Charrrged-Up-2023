package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;

public class RotateBellyObjectToPosition extends RotatingBellyCommand {
    private static double timeToEnd = 5;
    public RotateBellyObjectToPosition(double timeToEndSeconds){
        timeToEnd = timeToEndSeconds;
    }

    public RotateBellyObjectToPosition(){}

    @Override
    public void execute() {
        belly.setSpeed(RobotMap.RotatingBelly.PERCENTAGE_ROTATING_SPEED);
    }

    @Override
    public boolean isFinished() {
        Timer.delay(timeToEnd);
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        belly.stop();
    }
}
