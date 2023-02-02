package edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands.claw;

import edu.greenblitz.tobyDetermined.commands.TelescopicArm.ClawCommand;

public class RunClawGrip extends ClawCommand {

    public RunClawGrip(){

    }

    @Override
    public void initialize() {
        claw.grip();
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
