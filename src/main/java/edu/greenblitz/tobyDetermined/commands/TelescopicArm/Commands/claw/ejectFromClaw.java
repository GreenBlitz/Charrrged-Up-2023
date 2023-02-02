package edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands.claw;

import edu.greenblitz.tobyDetermined.commands.TelescopicArm.ClawCommand;

public class ejectFromClaw extends ClawCommand {

    public ejectFromClaw(){

    }

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
