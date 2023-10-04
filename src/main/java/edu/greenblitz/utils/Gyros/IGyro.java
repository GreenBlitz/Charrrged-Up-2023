package edu.greenblitz.utils.Gyros;

public interface IGyro {

	double getYaw();

	double getPitch();

	double getRoll();

	void setYawAngle(double yaw);
	void setPitchAngle(double pitch);

	void setRollAngle(double roll);


}
