package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSim;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ExtenerSim;

public class StayAtCurrentLength extends AutoExtenderCommand {

    @Override
    public void execute() {
        extender.setMotorVoltage(ExtenerSim.getStaticFeedForward(ElbowSim.getInstance().getAngle()));
    }
}
