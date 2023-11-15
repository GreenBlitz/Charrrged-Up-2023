package edu.greenblitz.utils;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import org.opencv.core.Mat;

public class GBMath {
	/**
	 *
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
	public static Translation2d polarToCartesian(double length, double angle) {
		return  new Translation2d(length*Math.cos(angle),length* Math.sin(angle));
	}
	public static Pair<Double,Double> cartesianToPolar(double x, double y) {
		return  new Pair<>(Math.sqrt(x*x+y*y),Math.atan2(y,x));
	}
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
	}
	public static double distance(Translation2d p1, Translation2d p2) {
		return Math.sqrt(Math.pow(p1.getX()- p2.getX(),2)+Math.pow(p1.getY()-p2.getY(),2));
	}
	public static double sigmoid(double x, double size, double uniformity, double xMovement) {
		return size /(1+Math.exp(uniformity*(xMovement-x)));
	}
	public static double lawOfCosines(double sideA, double sideB, double angleBetweenSideAndSideB) {
		return Math.sqrt(sideA*sideA+sideB*sideB-2*sideA*sideB*Math.cos(angleBetweenSideAndSideB));
	}
	public static double getBow(double length1, double angle1, double length2, double angle2){
		double angle  = Math.abs(angle1-angle2);
		return length1*angle;
	}
}
