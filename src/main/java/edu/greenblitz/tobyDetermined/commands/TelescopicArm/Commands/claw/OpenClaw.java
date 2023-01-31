package edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands.claw;

import edu.greenblitz.tobyDetermined.commands.TelescopicArm.ClawCommand;

public class OpenClaw extends ClawCommand {

    public OpenClaw(){

    }

    @Override
    public void initialize() {
        claw.open();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
