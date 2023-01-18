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
	public void execute(){
		pigeon = swerve.getPigeonGyro();
		new RotateAllWheelsToAngle(Units.degreesToRadians(pigeon.getRoll() - Units.degreesToRadians(180))).schedule();
	}


	
}
