package edu.greenblitz.tobyDetermined.commands.Auto.balance;

import edu.greenblitz.tobyDetermined.commands.swerve.RotateAllWheelsToAngle;
import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.utils.PigeonGyro;

// import edu.wpi.first.math.geometry.Rotation2d;
public class LockWheels extends SwerveCommand {
	
	PigeonGyro pigeon;


	public LockWheels(){}

	@Override
	public void initialize(){
		pigeon = swerve.getPigeonGyro();
		new RotateAllWheelsToAngle(Math.toDegrees(90) - pigeon.getYaw()).schedule();
		swerve.setIdleModeBrake();
	}
	
	@Override
	public boolean isFinished() {
		return true;
	}
}
