package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.wpi.first.wpilibj.Timer;

public class GripFromClaw extends ClawCommand {
    private static final double gripTime = 2; //seconds
    @Override
    public void initialize() {
        claw.grip();
    }

    @Override
    public boolean isFinished() {
        Timer.delay(gripTime);
        return true;
    }

    @Override
    public void end(boolean interrupted) {

        claw.stopMotor();
    }
}
