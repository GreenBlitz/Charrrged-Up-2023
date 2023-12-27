package edu.greenblitz.utils;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.MAX_ANGULAR_VELOCITY;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Extender.MAX_EXTENDER_VELOCITY;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Extender.STARTING_LENGTH;
import static edu.greenblitz.utils.GBMath.limit;
import static edu.greenblitz.utils.GBMath.sigNum;

public class PolarArmStraightLineUtil {

    public static double getRatioBetweenAngleAndLength(double sideA, double sideB, double angleBetween) {
        double sideC = GBMath.lawOfCosines(sideA, sideB, angleBetween);
        double height = sideB * Math.sin(angleBetween);
        double adjacent = Math.sqrt(sideC * sideC - height * height);
        return height / adjacent;
    }

    public static double calculateExtenderVelocity(double ratio, double startLength, double targetLength, double velocity) {
        double signOfExtender = sigNum(targetLength, startLength);
        double extenderVelocity = Math.sqrt(velocity * velocity / (ratio * ratio + 1));
        double wantedVelocity = signOfExtender * extenderVelocity;

        wantedVelocity = limit(wantedVelocity, MAX_EXTENDER_VELOCITY);

        return wantedVelocity;
    }

    public static double calculateAngularVelocity(double startVelocity, double startAngle, double targetAngle, double currentLength) {
        double signOfAngle = sigNum(targetAngle, startAngle);
        double magnitudeOfVelocity = startVelocity / (currentLength + STARTING_LENGTH);

        magnitudeOfVelocity = limit(magnitudeOfVelocity, MAX_ANGULAR_VELOCITY);

        return signOfAngle * Math.abs(magnitudeOfVelocity);
    }
}
