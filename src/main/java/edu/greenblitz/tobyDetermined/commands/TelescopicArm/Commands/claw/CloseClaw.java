package edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands.claw;

import edu.greenblitz.tobyDetermined.commands.TelescopicArm.ClawCommand;

public class CloseClaw extends ClawCommand {

    public CloseClaw(){

    }

    @Override
    public void initialize() {
        claw.close();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
