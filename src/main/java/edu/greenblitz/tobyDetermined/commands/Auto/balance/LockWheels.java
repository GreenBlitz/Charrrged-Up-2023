package edu.greenblitz.tobyDetermined.commands.Auto.balance;

import edu.greenblitz.tobyDetermined.commands.swerve.RotateAllWheelsToAngle;
import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.tobyDetermined.subsystems.GyroIOs.IGyro;

// import edu.wpi.first.math.geometry.Rotation2d;
public class LockWheels extends SwerveCommand {

	IGyro pigeon;

	public LockWheels() {
	}

	@Override
	public void initialize() {
		pigeon = swerve.getGyro();
		new RotateAllWheelsToAngle(Math.toDegrees(90) - pigeon.getYaw()).schedule();
		swerve.setIdleModeBrake();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
