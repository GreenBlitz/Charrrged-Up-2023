package edu.greenblitz.utils.Gyros;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

public class NavX implements IGyro{
	private AHRS gyro;
	private double yawOffset;
	private double pitchOffset;
	private double rollOffset;

	public NavX(){
		this.gyro = new AHRS(SerialPort.Port.kMXP);

		yawOffset = 0;
		pitchOffset = 0;
		rollOffset = 0;
	}

	@Override
	public double getYaw() {
		return Math.IEEEremainder((Math.toRadians(gyro.getYaw()) - yawOffset), 2 * Math.PI);
	}

	@Override
	public double getPitch() {
		return ((Math.toRadians(gyro.getPitch()) - pitchOffset)%( 2 * Math.PI));
	}

	@Override
	public double getRoll() {
		return ((Math.toRadians(gyro.getRoll()) - rollOffset)%(2* Math.PI));
	}

	@Override
	public void setYawAngle(double yaw) {
		yawOffset += (yaw + getYaw());
	}

	@Override
	public void setPitchAngle(double pitch) {
		pitchOffset += (pitch + getYaw());
	}

	@Override
	public void setRollAngle(double roll) {
		rollOffset += (roll + getRoll());
	}
}
