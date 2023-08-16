package edu.greenblitz.tobyDetermined.commands.armarmCom.clawClaw;

import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.ClawCommand;

public class GripConeCone extends ClawClawCommand {
    public void initialize() {
        claw.coneCatchMode();
    }

    @Override
    public void execute() {
        claw.motorGrip();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        claw.stopMotor();
    }
}
