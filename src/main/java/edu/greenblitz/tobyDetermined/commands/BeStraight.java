package edu.greenblitz.tobyDetermined.commands;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;

import java.util.ArrayList;

public class BeStraight extends GBCommand {
    ElbowSub elbowSub;
    Extender extender;
    ArrayList<Double> angleSpeeds = new ArrayList<>();
    ArrayList<Double> lengthSpeeds = new ArrayList<>();
    public void ElbowSub (double lengthInMeters, double angleInRads, double timeInSec) {


        angleSpeeds = new ArrayList<>();
        lengthSpeeds = new ArrayList<>();

        //1 - find the change of angle and length
        double alpha = angleInRads - elbowSub.getAngleRadians();
        double curLength = extender.getLength();
        double endLength = lengthInMeters;

        //2 - split to spaces using cosine rule
        double distance = Math.sqrt(curLength*curLength+ endLength*endLength - 2* curLength*endLength*Math.cos(alpha));
        int numOfSpaces = (int) (distance/2); // magic number

        //3 - how much time each iteration should take
        double timeForIteration = timeInSec/numOfSpaces; //magic number

        //4,5 - interpolation , call motors
        for (int i = 1; i<numOfSpaces; i++){
            double t = (double) i/numOfSpaces;
            double intermediateA = elbowSub.getAngleRadians() + t * (angleInRads - elbowSub.getAngleRadians());
            double intermediateL = extender.getLength() + t * (lengthInMeters - extender.getLength());
            double velocityA = intermediateA/timeForIteration;
            angleSpeeds.add(velocityA);
            double velocityL = intermediateL/timeForIteration;
            lengthSpeeds.add(velocityL);

        }
        double velocityA = angleInRads/timeForIteration;
        angleSpeeds.add(velocityA);
        double velocityL = lengthInMeters/timeForIteration;
        lengthSpeeds.add(velocityL);

        //double ratio = a/l;
        //double speed = 3;
        //double speedL = Math.sqrt(Math.pow(speed,speed)/(1+ratio*ratio));
        //double speedA = speedL*ratio;

        //setLinSpeed();
        //setAngSpeed();

    }

    public ArrayList<Double> getAngleSpeeds(){
        return angleSpeeds;
    }
    public ArrayList<Double> getLengthSpeeds(){
        return lengthSpeeds;
    }
}
