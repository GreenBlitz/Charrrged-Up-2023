package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

public class EjectFromClaw extends ClawCommand {

    @Override
    public void initialize() {
        claw.eject();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        claw.stopMotor();
    }
}
