package edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands.claw;

import edu.greenblitz.tobyDetermined.commands.TelescopicArm.ClawCommand;

public class StopClaw extends ClawCommand {
    public StopClaw(){}

    @Override
    public void initialize() {
        claw.stopMotor();
    }
}
