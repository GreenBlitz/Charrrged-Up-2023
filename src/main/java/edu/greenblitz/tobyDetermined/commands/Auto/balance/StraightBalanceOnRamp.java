package edu.greenblitz.tobyDetermined.commands.Auto.balance;

import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.tobyDetermined.subsystems.Gyros.IGyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StraightBalanceOnRamp extends SwerveCommand {

    private final IGyro gyro;
    private double currentAngle = 0;
    private double lastAngle = 0;
    private boolean hasPassedHighPoint;
    private final double HIGH_POINT = Math.toRadians(16);
    private final double MIN_ANGLE_CHANCE_TO_STOP = Math.toRadians(0.25);
    private final double SPEED = 0.25;

    public StraightBalanceOnRamp() {
        this.gyro = swerve.getGyro();
    }

    @Override
    public void execute() {
        lastAngle = currentAngle;
        currentAngle = Math.abs(gyro.getRoll());
        swerve.moveByChassisSpeeds(SPEED * -Math.signum(gyro.getRoll()), 0, 0, 0);

        if (currentAngle > HIGH_POINT) {
            hasPassedHighPoint = true;
        }
    }

    @Override
    public boolean isFinished() {
        if (currentAngle - lastAngle <= -1 * MIN_ANGLE_CHANCE_TO_STOP && hasPassedHighPoint) {
            SmartDashboard.putBoolean("on", false);
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        swerve.stop();
        hasPassedHighPoint = false;
    }
}
