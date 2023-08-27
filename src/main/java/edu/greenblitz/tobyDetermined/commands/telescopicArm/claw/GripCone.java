package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

public class GripCone extends ClawCommand {


    @Override
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
