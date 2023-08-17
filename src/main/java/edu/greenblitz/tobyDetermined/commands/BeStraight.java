package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;

public class BeStraight extends GBCommand {
    ElbowSub elbowSub;
    Extender extender;

    public void ElbowSub (double lengthInMeters, double angleInRads, double timeInSec) {

        //1 - find the change of angle and length
        double l = lengthInMeters - extender.getLength();
        double a = angleInRads - elbowSub.getAngleRadians();

        //2 - split to spaces
        double distance = Math.sqrt(a*a+l*l);
        int numOfSpaces = (int) (distance/2); // magic number

        //2 - how much time each iteration should take
        double timeForIteration = timeInSec/numOfSpaces; //magic number

        //4,5 - interpolation , call motors
        for (int i = 1; i<numOfSpaces; i++){
            double t = (double) i/numOfSpaces;
            double intermediateA = elbowSub.getAngleRadians() + t * (angleInRads - elbowSub.getAngleRadians());
            double intermediateL = extender.getLength() + t * (lengthInMeters - extender.getLength());
            double velocityA = intermediateA/timeForIteration;
            double velocityL = intermediateL/timeForIteration;
        }
        double velocityA = angleInRads/timeForIteration;
        double velocityL = lengthInMeters/timeForIteration;

        //double ratio = a/l;
        //double speed = 3;
        //double speedL = Math.sqrt(Math.pow(speed,speed)/(1+ratio*ratio));
        //double speedA = speedL*ratio;

    }
}
