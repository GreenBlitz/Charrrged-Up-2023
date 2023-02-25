package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.utils.PigeonGyro;
import edu.greenblitz.utils.PitchRollAdder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AdvancedBalanceOnRamp extends SwerveCommand {

    private final PigeonGyro gyro;
    private final double highPoint = Math.toRadians(16);
    private final double minAngleChangeToStop = Math.toRadians(0.1);
    private double speed = 0.25;
    private double currentAngle = 0;
    private double lastAngle = 0;
    private boolean hasPassedHighPoint;
    private boolean forward;

    public AdvancedBalanceOnRamp(boolean forward) {
        this.gyro = swerve.getPigeonGyro();
        this.forward=forward;
        if (!forward){
            speed *= -1;
        }
    }

    @Override
    public void execute() {
        lastAngle = currentAngle;
        currentAngle = Math.abs(PitchRollAdder.add(gyro.getPitch(),gyro.getRoll()));

        swerve.moveByChassisSpeeds(speed , 0, 0, gyro.getYaw());

        if (currentAngle > highPoint) {
            hasPassedHighPoint = true;
        }
    }

    @Override
    public boolean isFinished() {
        if (currentAngle - lastAngle <= -1 * minAngleChangeToStop && hasPassedHighPoint) {
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
