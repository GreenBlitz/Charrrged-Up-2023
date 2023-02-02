package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

public class RunClawGrip extends ClawCommand {

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
