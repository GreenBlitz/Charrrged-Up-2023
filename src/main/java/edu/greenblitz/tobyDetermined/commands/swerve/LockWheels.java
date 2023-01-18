package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.PigeonGyro;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// import edu.wpi.first.math.geometry.Rotation2d;
public class LockWheels extends SwerveCommand{
	
	PigeonGyro pigeon;


	public LockWheels(){}

	@Override
	public void initialize(){
		pigeon = swerve.getPigeonGyro();
		new RotateAllWheelsToAngle(Math.PI / 2 - pigeon.getYaw()).schedule();
	}
	
	@Override
	public boolean isFinished() {
		return true;
	}
}
