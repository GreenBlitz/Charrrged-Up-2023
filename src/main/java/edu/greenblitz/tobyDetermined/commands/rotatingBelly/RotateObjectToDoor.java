package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;

public class RotateObjectToDoor extends RotatingBellyCommand {
    private static double timeToEnd = 3;
    public RotateObjectToDoor(double timeToEndSeconds){
        timeToEnd = timeToEndSeconds;
    }

    public RotateObjectToDoor(){}

    @Override
    public void execute() {
        belly.setPower(RobotMap.RotatingBelly.PERCENTAGE_ROTATING_SPEED);
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
