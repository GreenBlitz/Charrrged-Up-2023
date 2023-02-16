package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;

public class RotateInDoorDirection extends RotatingBellyCommand {

    @Override
    public void execute() {
        belly.setPower(RobotMap.RotatingBelly.ROTATING_POWER);
    }

    @Override
    public void end(boolean interrupted) {
        belly.stop();
    }
}
