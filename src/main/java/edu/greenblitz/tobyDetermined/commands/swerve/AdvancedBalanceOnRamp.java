package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.utils.PigeonGyro;
import edu.greenblitz.utils.PitchRollAdder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// advance until ramp starts falling forwards, then move backwards fixed duration
public class AdvancedBalanceOnRamp extends SwerveCommand {

    private final PigeonGyro gyro;
    private final double highPoint = Math.toRadians(16);
    private final double minAngleChangeToStop = Math.toRadians(0.1);
    private double speed = 0.25;
    private double phase2speed = -0.1; // negative as phase 2 is in the opposite direction
    private double phase2duration = 0.5;
    private double currentAngle = 0;
    private double lastAngle = 0;
    private boolean hasPassedHighPoint;

    public AdvancedBalanceOnRamp(boolean forward) {
        this.gyro = swerve.getPigeonGyro();
        if (!forward){
            speed *= -1;
            phase2speed *= -1;
        }
    }

    @Override
    public void execute() {
        lastAngle = currentAngle;
        currentAngle = Math.abs(PitchRollAdder.add(gyro.getRoll(),gyro.getPitch()));

        swerve.moveByChassisSpeeds(speed , 0, 0, gyro.getYaw());

        if (currentAngle > highPoint) {
            hasPassedHighPoint = true;
        }
    }

    @Override
    public boolean isFinished() {
        if (currentAngle - lastAngle <= -minAngleChangeToStop && hasPassedHighPoint) {
            SmartDashboard.putBoolean("on", false);
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted){
            swerve.stop();
            hasPassedHighPoint = false;
            return;
        }
        new MoveDuration(phase2duration, phase2speed).schedule();
    }
}
