package edu.greenblitz.utils;

import edu.wpi.first.math.Pair;
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
	public static Pair<Double,Double> polarToCartesian(double length, double angle) {
		return  new Pair<>(length*Math.cos(angle),length* Math.sin(angle));
	}
	public static Pair<Double,Double> cartesianToPolar(double x, double y) {
		return  new Pair<>(Math.sqrt(x*x+y*y),Math.atan2(y,x));
	}
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
	}
	public static double distance(Pair<Double,Double> p1, Pair<Double,Double> p2) {
		return Math.sqrt(Math.pow(p1.getFirst()- p2.getFirst(),2)+Math.pow(p1.getSecond()-p2.getSecond(),2));
	}
	public static double lawOfCosines(double sideA, double sideB, double angleBetweenSideAndSideB) {
		return Math.sqrt(sideA*sideA+sideB*sideB-2*sideA*sideB*Math.cos(angleBetweenSideAndSideB));
	}
	
}
