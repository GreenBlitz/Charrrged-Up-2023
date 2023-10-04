package edu.greenblitz.tobyDetermined.commands.Auto.balance.bangBangBalance;

import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.wpi.first.math.filter.Debouncer;

public class BangBangBalance extends SwerveCommand {

	private double originalSpeed;
	private double usedSpeed;
	private final double USEDSPEED_FACTOR = 0.36;
	public static final double TOLERANCE = Math.toRadians(13);

	public static final double FINISH_TOLERANCE = Math.toRadians(7);

	private double usedTolerance;

	private Debouncer debouncer;
	private final double DEBOUNCE_TIME = 5;

	double pitchAngle;
	private boolean forwards;

	public BangBangBalance(double speed) {
		this.originalSpeed = speed;
		forwards = speed > 0;
	}

	@Override
	public void initialize() {
		pitchAngle = 0;
		debouncer = new Debouncer(DEBOUNCE_TIME);
		usedSpeed = originalSpeed;
		usedTolerance = TOLERANCE;
	}

	@Override
	public void execute() {
		if ((pitchAngle * swerve.getNavX().getRoll() * (forwards ? -1 : 1)) < 0) {
			usedSpeed = originalSpeed * USEDSPEED_FACTOR;
		}
		pitchAngle = swerve.getNavX().getRoll() * (forwards ? -1 : 1);//gyro is flipped
		if (Math.abs(pitchAngle) > usedTolerance) {
			swerve.moveByChassisSpeeds(usedSpeed * Math.signum(pitchAngle), 0.0, 0.0, 0);
		} else {
			swerve.stop();
		}
		if (Math.abs(pitchAngle) < FINISH_TOLERANCE) {
			usedTolerance = FINISH_TOLERANCE;
		}
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		swerve.stop();
	}
}
