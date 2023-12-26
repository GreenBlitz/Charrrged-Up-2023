package edu.greenblitz.utils;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Translation2d;

public class GBMath {

    public static double getRatioBetweenAngleAndLength(double sideA, double sideB, double angleBetween) {
        double sideC = GBMath.lawOfCosines(sideA, sideB, angleBetween);
        double height = sideB * Math.sin(angleBetween);
        double adjacent = Math.sqrt(sideC * sideC - height * height);
        return height / adjacent;
    }

    public static double limit(double velocity, double maxMagnitude) {
        return limit(velocity, -maxMagnitude, maxMagnitude);
    }

    public static double limit(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }

    /**
     * modulo (2,6) -> 0
     * modulo (2,3) -> 1
     * modulo (-2,3) -> 1
     * modulo (3,-2) -> -1 -> 1
     *
     * @param x the number to be modulu-ed
     * @param y the modulo base
     * @return the correct modulo result
     */
    public static double absoluteModulo(double x, double y) {
        return ((x % y) + y) % y;
    }

    /**
     * Takes a length and angle and converts it into x and y
     *
     * @param length the length of the point
     * @param angle  the angle of the point
     * @return a Translation2d which houses the result
     */
    public static Translation2d convertToCartesian(double length, double angle) {
        return new Translation2d(length * Math.cos(angle), length * Math.sin(angle));
    }

    /**
     * Takes an x and y and converts it into polar coordinates
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @return a Pair which houses the result in this format (length,angle)
     */
    public static Pair<Double, Double> convertToPolar(double x, double y) {
        return new Pair<>(Math.sqrt(x * x + y * y), Math.atan2(y, x));
    }

    /**
     * Takes an x and y position and speeds, and radius of polar conversion and converts it into polar speeds
     *
     * @param x      the x coordinate of the point
     * @param y      the y coordinate of the point
     * @param xVelocity the x Velocity of the point
     * @param yVelocity the y Velocity of the point
     * @return a Pair which houses the result in this format (length,angle)
     */
    public static Pair<Double, Double> convertToPolarSpeeds(double x, double y, double xVelocity, double yVelocity) {

        double distance = Math.sqrt(x * x + y * y);

        double lengthVelocity = x * (xVelocity) + y * (yVelocity);
        lengthVelocity /= distance;

        double angularVelocity = x * yVelocity - y * xVelocity;
        angularVelocity /= distance;
        return new Pair<>(lengthVelocity, angularVelocity);
    }

    public static double distance(Translation2d point1, Translation2d point2) {
        return Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2));
    }

    /**
     * A useful interpolation function
     *
     * @param x          the variable of the function
     * @param size       a constant scaling of the equation
     * @param uniformity a kind of uniformity term
     * @param xMovement  a translation of the function to the left by xMovement units
     * @return a Pair which houses the result in this format (length,angle)
     */
    public static double sigmoid(double x, double size, double uniformity, double xMovement) {
        return size / (1 + Math.exp(uniformity * (xMovement - x)));
    }

    public static double lawOfCosines(double sideA, double sideB, double angleBetweenSideAndSideB) {
        return Math.sqrt(sideA * sideA + sideB * sideB - 2 * sideA * sideB * Math.cos(angleBetweenSideAndSideB));
    }

    public static double getBow(double length1, double angle1, double length2, double angle2) {
        double angle = Math.abs(angle1 - angle2);
        return length1 * angle;
    }


}

