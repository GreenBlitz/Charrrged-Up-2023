package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;

public class BeStraight extends GBCommand {
    ElbowSub elbowSub;
    Extender extender;

    public String ElbowSub (double lengthInMeters, double angleInRads) {
        double l = extender.getLength() - lengthInMeters;
        double a = elbowSub.getAngleRadians() - angleInRads;
        double ratio = a/l;
        double speed = 3;
        double speedL = Math.sqrt(Math.pow(speed,speed)/(1+ratio*ratio));
        double speedA = speedL*ratio;
        return "The speed of angle: "+speedA+"  The speed of extend"+ speedL;
    }
}
