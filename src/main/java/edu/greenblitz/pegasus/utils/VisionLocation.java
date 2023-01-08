package edu.greenblitz.pegasus.utils;

public class VisionLocation {
	/**
	 * X coordinate of the target (horizontal distance)
	 */
	public final double x;

	/**
	 * Y coordinate of the data (depth)
	 */
	public final double y;

	/**
	 * Z coordinate of the data (vertical distance)
	 */
	public final double z;

	/**
	 * The angle of the target, relative to the robot
	 */

	public VisionLocation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public VisionLocation(double[] rawData) {
		this(rawData[0], rawData[2], rawData[1]);
	}

	public static double calculateRelativeAngle(double x, double z) {
		if (x / z == 90) {
			return Math.toRadians(361);
		}
		return Math.atan2(x, z);
	}

	public double getFullDistance() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * @return distance to target ignoring z component
	 */
	public double getPlaneDistance() {
		return Math.hypot(x, y);
	}

	public double getRelativeAngle() {
		return Math.toDegrees(calculateRelativeAngle(this.x, this.y));
	}

	public double getRelativeAngleRad() {
		return calculateRelativeAngle(this.x, this.y);
	}

	public boolean isValid() {
		return Double.isFinite(x) && Double.isFinite(y) && Double.isFinite(z);
	}

	public double[] toDoubleArray() {
		return new double[]{this.x, this.y, this.z};
	}

	@Override
	public String toString() {
		return "VisionLocation{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}


}