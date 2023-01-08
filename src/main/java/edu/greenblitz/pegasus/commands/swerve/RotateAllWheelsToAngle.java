package edu.greenblitz.pegasus.commands.swerve;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class RotateAllWheelsToAngle extends SwerveCommand{
	private double targetAngle;

	public RotateAllWheelsToAngle(double targetAngleInRads){
		this.targetAngle = targetAngleInRads;
	}

	@Override
	public void execute() {
		swerve.setModuleStates(new SwerveModuleState[]{
				new SwerveModuleState(0,new Rotation2d(targetAngle)),
				new SwerveModuleState(0,new Rotation2d(targetAngle)),
				new SwerveModuleState(0,new Rotation2d(targetAngle)),
				new SwerveModuleState(0,new Rotation2d(targetAngle))});
	}
}
