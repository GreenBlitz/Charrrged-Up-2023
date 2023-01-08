package edu.greenblitz.pegasus.utils;


import edu.greenblitz.pegasus.utils.cords.Point;
import edu.greenblitz.pegasus.utils.cords.Position;

/**
 * A tool in order to calculate robot position using encoders (and optionally a gyro). Recommended to run in a different
 * thread, at about one execution per 10 ms.<br><br>
 * <p>
 * Example 1:<br>
 * https://github.com/GreenBlitz/Deep-Space-Robot/blob/detroit/src/main/java/edu/greenblitz/knockdown/data/LocalizerRunner.java
 * <br><br>
 * Example 2 (private until 2020 season competitions):<br>
 * https://github.com/GreenBlitz/Infinite-Reee-2020/blob/a1bfc27b303ceaa40902aed94c376bb87e000269/src/main/java/edu/greenblitz/bigRodika/commands/chassis/LocalizerCommand.java
 * <br><br>
 *
 * @author Udi    ~ MudiAtalon
 * @author Alexey ~ savioor
 * @author Or Karl
 */
public class Localizer {

	private static final Localizer instance = new Localizer();
	private final Object LOCK = new Object();
	private Position location = new Position(0, 0, 0); // Positive x direction is left
	private double wheelDistance;
	private double angle0;
	private double prevDistanceLeft;
	private double prevDistanceRight;
	private double zeroDistanceLeft, zeroDistanceRight;

	private Localizer() {
		angle0 = 0;
	}

	public static Localizer getInstance() {
		return instance;
	}

	public static Point calculateMovement(double rightDist, double leftDist, double wheelDistance, double robotAng) {
		if (rightDist == leftDist) {
			return new Point(0, rightDist).rotate(robotAng);
		}

		double distance = (rightDist + leftDist) / 2;
		double angle = (rightDist - leftDist) / wheelDistance;
		double circleRadius = distance / angle;

		double dy = circleRadius * Math.sin(angle);
		double dx = circleRadius * (1 - Math.cos(angle));
		return new Point(dx, dy).rotate(robotAng);
	}

	/**
	 * @return The calculated location of the robot such that positive y is forwards, positive x is left. The 0 angle is
	 * facing positive y and increasing counter-clockwise.
	 */
	public Position getLocationRaw() {
		synchronized (LOCK) {
			return location.clone();
		}
	}

	/**
	 * @return The calculated location of the robot such that positive y is forwards, positive x is right. The 0 angle is
	 * facing positive y and increasing counter-clockwise.
	 */
	public Position getLocation() {
		Position pos = getLocationRaw();
		pos.setX(-pos.getX());
		return pos;
	}

	/**
	 * @param wheelDistance The distance between the left and right wheel set
	 * @param leftDist      The meters counted on the left encoder
	 * @param rightDist     The meters counter on the right encoder
	 */
	public void configure(double wheelDistance, double leftDist, double rightDist) {
		this.wheelDistance = wheelDistance;
		reset(leftDist, rightDist);
	}

	/**
	 * @param currentLeftDistance  The meters counted on the left encoder
	 * @param currentRightDistance The meters counter on the right encoder
	 * @param newPos               The new position to set the localizer to
	 */
	public void reset(double currentLeftDistance, double currentRightDistance, Position newPos) {
		synchronized (LOCK) {
			prevDistanceLeft = currentLeftDistance;
			prevDistanceRight = currentRightDistance;
			zeroDistanceLeft = currentLeftDistance;
			zeroDistanceRight = currentRightDistance;
			angle0 = newPos.getAngle();
			location = newPos.clone();
		}
	}

	/**
	 * This keeps the same location as before, only resetting encoders. Use this when
	 * you reset your own encoders to avoid localizer jumps.
	 *
	 * @param currLeft  The meters counted on the left encoder
	 * @param currRight The meters counter on the right encoder
	 */
	public void resetEncoders(double currLeft, double currRight) {
		reset(currLeft, currRight, getLocationRaw());
	}

	/**
	 * rests the encoders and set the location to 0, 0, 0
	 *
	 * @param currentLeftDistance  The meters counted on the left encoder
	 * @param currentRightDistance The meters counter on the right encoder
	 */
	public void reset(double currentLeftDistance, double currentRightDistance) {
		reset(currentLeftDistance, currentRightDistance, new Position(0, 0, 0));
	}

	/**
	 * Angle is calculated using the encoders.
	 *
	 * @param currentLeftDistance  Current left wheel distance
	 * @param currentRightDistance Current right wheel distance
	 */
	public void update(double currentLeftDistance, double currentRightDistance) {
		double ang = (((currentRightDistance - zeroDistanceRight)
				- (currentLeftDistance - zeroDistanceLeft)) / wheelDistance);

		update(currentLeftDistance, currentRightDistance, ang);
	}

	/**
	 * @param currentLeftDistance  Current left wheel distance
	 * @param currentRightDistance Current right wheel distance
	 * @param angle                The current angle of the robot in radians, in the same coordinate system as specified in getLocation
	 */
	public void update(double currentLeftDistance, double currentRightDistance, double angle) {
		double rDist, lDist;
		synchronized (LOCK) {
			rDist = currentRightDistance - prevDistanceRight;
			lDist = currentLeftDistance - prevDistanceLeft;
		}

		Point dXdY = calculateMovement(
				rDist, lDist,
				wheelDistance, location.getAngle());

		synchronized (LOCK) {
			location.translate(dXdY);
			location.setAngle(angle + angle0);
			prevDistanceLeft = currentLeftDistance;
			prevDistanceRight = currentRightDistance;
		}

	}

}