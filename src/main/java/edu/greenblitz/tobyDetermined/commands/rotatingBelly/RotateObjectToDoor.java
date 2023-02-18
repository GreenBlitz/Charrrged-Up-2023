package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.Timer;

public class RotateObjectToDoor extends RotatingBellyCommand {
    public RotateObjectToDoor(double timeToEndSeconds){
        RobotMap.RotatingBelly.ROTATE_TO_DOOR_TIME = timeToEndSeconds;
    }

    public RotateObjectToDoor(){}

    @Override
    public void execute() {
        belly.setPower(RobotMap.RotatingBelly.ROTATING_POWER);
    }

    @Override
    public boolean isFinished() {
        Timer.delay(RobotMap.RotatingBelly.ROTATE_TO_DOOR_TIME);
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        belly.stop();
    }
}
