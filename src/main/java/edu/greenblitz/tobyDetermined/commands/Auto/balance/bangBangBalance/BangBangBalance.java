package edu.greenblitz.tobyDetermined.commands.Auto.balance.bangBangBalance;

import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.wpi.first.math.filter.Debouncer;

public class BangBangBalance extends SwerveCommand {

    private double originalSpeed;
    private double usedSpeed;
    private final double USEDSPEED_FACTOR = 0.5;
    public static final double TOLERANCE = Math.toRadians(2);

    private Debouncer debouncer;
    private final double DEBOUNCE_TIME = 0.3;

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
    }

    @Override
    public void execute() {
        if ((pitchAngle * swerve.getPigeonGyro().getRoll() * (forwards ? -1 : 1)) < 0) {
            usedSpeed = usedSpeed * USEDSPEED_FACTOR;
        }
        pitchAngle = swerve.getPigeonGyro().getRoll() * (forwards ? -1 : 1);//gyro is flipped
        if (Math.abs(pitchAngle) > TOLERANCE) {
            swerve.moveByChassisSpeeds(usedSpeed * Math.signum(pitchAngle), 0.0, 0.0, 0);
        } else {
            swerve.stop();
        }
    }

    @Override
    public boolean isFinished() {
        boolean debounced = debouncer.calculate(Math.abs(pitchAngle) < TOLERANCE);
        return super.isFinished() || debounced;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        swerve.stop();

    }
}
