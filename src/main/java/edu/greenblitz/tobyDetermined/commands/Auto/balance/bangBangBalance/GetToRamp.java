package edu.greenblitz.tobyDetermined.commands.Auto.balance.bangBangBalance;

import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.wpi.first.math.filter.Debouncer;

public class GetToRamp extends SwerveCommand {

	private double speed;
	private boolean forwards;
	private static final double TOLERANCE = Math.toRadians(5);

	private Debouncer debouncer;
	private final double DEBOUNCE_TIME = 0.1;


	double pitchAngle;

	public GetToRamp(double speed) {
		this.speed = speed;
		forwards = speed > 0;
	}

	@Override
	public void initialize() {
		pitchAngle = 0;
		debouncer = new Debouncer(DEBOUNCE_TIME);
	}

	@Override
	public void execute() {
		pitchAngle = swerve.getPigeonGyro().getRoll() * (forwards ? 1 : -1);//gyro is flipped
		swerve.moveByChassisSpeeds(speed, 0, 0, 0);
	}

	@Override
	public boolean isFinished() {
		return super.isFinished() || debouncer.calculate(Math.abs(pitchAngle) > TOLERANCE);
	}
}
