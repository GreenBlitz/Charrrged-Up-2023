package edu.greenblitz.tobyDetermined.commands.armarmCom.elbowElbow;

import edu.greenblitz.tobyDetermined.subsystems.armarm.ExtenderSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

public class StayAtCurrentAngleNode extends ElbowCommandElbow{
    @Override
    public void execute() {
        elbow.setMotorVoltage(ElbowSub.getStaticFeedForward(ExtenderSub.getInstance().getLength(),elbow.getAngleRadians()));
    }
}
