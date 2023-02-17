package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

public class StayAtCurrentAngle extends ElbowCommand {

    @Override
    public void execute() {
        elbow.setMotorVoltage(Elbow.getStaticFeedForward(Extender.getInstance().getLength(),elbow.getAngle()));
    }
}
