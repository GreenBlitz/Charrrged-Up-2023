package edu.greenblitz.tobyDetermined.commands.Auto.balance;

import edu.greenblitz.tobyDetermined.commands.swerve.RotateAllWheelsToAngle;
import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.GyroIOs.Gyro;
import edu.greenblitz.utils.Gyros.IGyro;
import edu.greenblitz.utils.Gyros.PigeonGyro;

// import edu.wpi.first.math.geometry.Rotation2d;
public class LockWheels extends SwerveCommand {

	Gyro pigeon;

	public LockWheels() {
	}

	@Override
	public void initialize() {
		pigeon = swerve.getPigeonGyro();
		new RotateAllWheelsToAngle(Math.toDegrees(90) - pigeon.getYaw()).schedule();
		swerve.setIdleModeBrake();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
