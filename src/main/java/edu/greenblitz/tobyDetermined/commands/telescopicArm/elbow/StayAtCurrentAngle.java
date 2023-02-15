package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSim;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ExtenerSim;

public class StayAtCurrentAngle extends ElbowCommand {

    @Override
    public void execute() {
        elbow.setMotorVoltage(ElbowSim.getStaticFeedForward(ExtenerSim.getInstance().getLength(),ElbowSim.getFeedForward(0, 0, 0, 0)));
    }
}
