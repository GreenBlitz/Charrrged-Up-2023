package edu.greenblitz.tobyDetermined.subsystems.swerve;

import edu.greenblitz.utils.PIDObject;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public interface SwerveModule {
	void rotateToAngle(double angle);
	
	double getModuleAngle();
	
	double getCurrentVelocity();

	double getCurrentMeters();

	SwerveModulePosition getCurrentPosition();
	
	void resetEncoderToValue(double angle);
	
	void resetEncoderToValue();
	
	// reads the absolute angle from the absolute encoder and puts it into the relative encoder
	void resetEncoderByAbsoluteEncoder(SwerveChassis.Module module);
	
	void configLinPID(PIDObject pidObject);
	
	void configAnglePID(PIDObject pidObject);
	
	void setLinSpeed(double speed);
	
	void stop();
	
	double getTargetAngle();
	
	double getTargetVel();
	double getMotorOutputCurrent();
	
	SwerveModuleState getModuleState();
	
	boolean isAtAngle(double targetAngleInRads, double errorInRads);
	boolean isAtAngle (double errorInRads);
	
	void setModuleState(SwerveModuleState moduleState);
	
	double getAbsoluteEncoderValue();
	
	void setRotPowerOnlyForCalibrations(double power);
	
	void setLinPowerOnlyForCalibrations(double power);

	void setLinIdleModeBrake ();
	void setLinIdleModeCoast ();
	void setRotIdleModeBrake();
	void setRotIdleModeCoast ();
	boolean isEncoderBroken();
}
