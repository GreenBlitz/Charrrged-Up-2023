package edu.greenblitz.tobyDetermined.commands.armarmCom.extenderEXTENDER;

import edu.greenblitz.tobyDetermined.subsystems.armarm.ExtenderSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

public class stayAtCURRENTLength extends ExtenderCommand123 {
    public void execute() {
        extender.setMotorVoltage(ExtenderSub.getStaticFeedForward(ElbowSub.getInstance().getAngleRadians()));
    }
}
