package edu.greenblitz.tobyDetermined.commands.Auto.balance;

import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.GyroIOs.Gyro;
import edu.greenblitz.utils.PitchRollAdder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// advance until ramp starts falling forwards, then move backwards fixed duration
public class AdvancedBalanceOnRamp extends SwerveCommand {

	private final Gyro gyro;
	private final double highPoint = Math.toRadians(16);
	private final double minAngleChangeToStop = Math.toRadians(0.1);
	private double speed = 0.25;
	private double currentAngle = 0;
	private double lastAngle = 0;
	private boolean hasPassedHighPoint;

	public AdvancedBalanceOnRamp(boolean forward) {
		this.gyro = swerve.getGyro();
		if (!forward) {
			speed *= -1;
		}
	}

	@Override
	public void execute() {
		lastAngle = currentAngle;
		currentAngle = Math.abs(PitchRollAdder.add(gyro.getRoll(), gyro.getPitch()));

		swerve.moveByChassisSpeeds(speed, 0, 0, gyro.getYaw());

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
		swerve.stop();
	}
}
