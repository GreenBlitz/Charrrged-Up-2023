package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

public class StayAtCurrentLength extends ExtenderCommand {

    @Override
    public void execute() {
        extender.setMotorVoltage(Extender.getStaticFeedForward(Elbow.getInstance().getAngleRadians()));
    }
}
