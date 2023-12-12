package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;

public class DefaultRotateWhenCube extends ClawCommand{

    @Override
    public void execute() {
        if (ObjectSelector.IsCube()) {
            claw.motorGrip(0.1);
        } else {
            claw.stopMotor();
        }
    }

    @Override
    public void end(boolean interrupted) {
        claw.stopMotor();
    }
}
