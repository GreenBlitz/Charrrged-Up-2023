package edu.greenblitz.tobyDetermined.subsystems.swerve;

import edu.greenblitz.utils.PIDObject;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class DecoyModule implements SwerveModule {
	public DecoyModule() {
	
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
	public double getCurrentMeters() {
		return 0;
	}

	@Override
	public SwerveModulePosition getCurrentPosition() {
		return null;
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
	public double getMotorOutputCurrent() {
		return 0;
	}

	@Override
	public SwerveModuleState getModuleState() {
		return new SwerveModuleState();
	}
	
	@Override
	public boolean isAtAngle(double targetAngleInRads, double errorInRads) {
		return true;
	}
	
	@Override
	public boolean isAtAngle(double errorInRads) {
		return true;
	}
	
	@Override
	public void setModuleState(SwerveModuleState moduleState) {
	
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

	@Override
	public void setLinIdleModeBrake() {

	}

	@Override
	public void setLinIdleModeCoast() {

	}

	@Override
	public void setRotIdleModeBrake() {

	}

	@Override
	public void setRotIdleModeCoast() {

	}
	
	@Override
	public boolean isEncoderBroken() {
		return false;
	}
}
