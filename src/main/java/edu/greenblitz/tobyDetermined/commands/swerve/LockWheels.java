package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.PigeonGyro;
import edu.wpi.first.math.geometry.Rotation2d;
public class LockWheels extends SwerveCommand{
	
	PigeonGyro pigeon;
	
	public LockWheels(){}
	
	@Override
	public void execute(){
		pigeon = new PigeonGyro(RobotMap.gyro.pigeonID);
		
//		new RotateAllWheelsToAngle()
	}
	
}
