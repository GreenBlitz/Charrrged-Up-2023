package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveModule;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.ejml.equation.IntegerSequence;
import org.w3c.dom.ranges.Range;

public class RotateAllWheelsToAngle extends SwerveCommand {
	private final double targetAngle;
	private final double threshold = Math.toRadians(4);
	int counter =0;
	
	public RotateAllWheelsToAngle(double targetAngleInRads) {
		this.targetAngle = targetAngleInRads % Math.PI;
	}
	
	@Override
	public void execute() {
		SwerveModuleState state = new SwerveModuleState(0, new Rotation2d(targetAngle));
		swerve.setModuleStates(new SwerveModuleState[]{
				state,state,state,state});
//		SmartDashboard.putNumber("a",state.speedMetersPerSecond);
//		SmartDashboard.putNumber("b",state.angle.getRadians());
		SmartDashboard.putNumber("counter", counter++);
	}
	
	@Override
	public boolean isFinished() {
		boolean allInPlace = true;
		for (SwerveChassis.Module  module: SwerveChassis.Module.values()) {
			double currentAngle = swerve.getModuleAngle(module) % Math.PI;
			boolean isInRange = false;
			for (int i = -1; i <= 1 ; i++) {
				isInRange |= (currentAngle +Math.PI*i < targetAngle + threshold && currentAngle+Math.PI*i > targetAngle - threshold);
			}
			allInPlace &= isInRange;
		}
		return allInPlace;
	}
	
	@Override
	public void end(boolean interrupted) {
	
	}
}