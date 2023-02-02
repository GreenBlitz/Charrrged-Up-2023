package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

public class StopClaw extends ClawCommand {
    @Override
    public void initialize() {
        claw.stopMotor();
    }
}
