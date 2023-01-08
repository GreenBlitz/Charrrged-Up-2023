package edu.greenblitz.pegasus.utils;

public class GBMath {

	/**
	 * @author Tal & Asaf
	 * */

	/**
	 * java's % is actually the remainder operator so it returns the wrong value for negative modulo
	 * this is a correct implementation of the modulus operator
	 *
	 * @param x the number to be modulu-ed
	 * @param y the modulo base
	 * @return the correct modulo result
	 *
	 */
	public static double  modulo(double x, double y) {
		return ((x % y) + y) % y;
	}


}
