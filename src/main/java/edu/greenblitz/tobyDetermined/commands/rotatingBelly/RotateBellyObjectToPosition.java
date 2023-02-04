package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

import edu.greenblitz.tobyDetermined.RobotMap;

public class RotateBellyObjectToPosition extends RotatingBellyCommand {

    @Override
    public void execute() {
        belly.setSpeed(RobotMap.RotatingBelly.PERCENTAGE_ROTATING_SPEED);
    }

    @Override
    public boolean isFinished() {
        return belly.isMacroSwitchPressed();
    }

    @Override
    public void end(boolean interrupted) {
        belly.stop();
    }
}
