package edu.greenblitz.pegasus.subsystems.swerve;

import edu.greenblitz.pegasus.utils.PIDObject;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class DecoyModule implements SwerveModule{
	public DecoyModule() {

	}

		@Override
	public void setModuleState(SwerveModuleState moduleState) {

	}

	@Override
	public void rotateToAngle(double angle) {

	}

	@Override
	public double getModuleAngle() {
		return 0;
	}

	@Override
	public double getCurrentVelocity() {
		return 0;
	}

	@Override
	public void resetEncoderToValue(double angle) {

	}

	@Override
	public void resetEncoderToValue() {

	}

	@Override
	public void resetEncoderByAbsoluteEncoder(SwerveChassis.Module module) {

	}

	@Override
	public void configLinPID(PIDObject pidObject) {

	}

	@Override
	public void configAnglePID(PIDObject pidObject) {

	}

	@Override
	public void setLinSpeed(double speed) {

	}

	@Override
	public void stop() {

	}

	@Override
	public double getTargetAngle() {
		return 0;
	}

	@Override
	public double getTargetVel() {
		return 0;
	}

	@Override
	public SwerveModuleState getModuleState() {
		return new SwerveModuleState();
	}

	@Override
	public double getAbsoluteEncoderValue() {
		return 0;
	}

	@Override
	public void setRotPowerOnlyForCalibrations(double power) {

	}
	
	@Override
	public void setLinPowerOnlyForCalibrations(double power) {
	
	}
}
