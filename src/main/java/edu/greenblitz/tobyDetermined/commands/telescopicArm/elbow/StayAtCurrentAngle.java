package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

public class StayAtCurrentAngle extends ElbowCommand {

    @Override
    public void execute() {
        elbow.setMotorVoltage(ElbowSub.getStaticFeedForward(Extender.getInstance().getLength(),elbow.getAngleRadians()));
    }
}
