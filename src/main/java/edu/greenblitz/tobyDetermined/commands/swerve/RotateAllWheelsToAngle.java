package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassis;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class RotateAllWheelsToAngle extends SwerveCommand {
	private final double targetAngle;
	private final double threshold = Math.toRadians(4);
	
	private int timeOutCounter =0;
	
	public RotateAllWheelsToAngle(double targetAngleInRads) {
		this.targetAngle = targetAngleInRads;
	}
	
	@Override
	public void execute() {
		SwerveModuleState state = new SwerveModuleState(0, new Rotation2d(targetAngle));
		swerve.setModuleStates(new SwerveModuleState[]{
				state,state,state,state});
		timeOutCounter++;
	}
	
	@Override
	public boolean isFinished() {
		boolean allInPlace = true;
		for (SwerveChassis.Module  module: SwerveChassis.Module.values()) {
			allInPlace &= swerve.moduleIsAtAngle(module,targetAngle, threshold);
		}
		return allInPlace || timeOutCounter > 50;
	}
}