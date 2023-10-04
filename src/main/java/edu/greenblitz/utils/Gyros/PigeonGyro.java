package edu.greenblitz.utils.Gyros;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.greenblitz.utils.SystemCheck.IPingable;
import edu.greenblitz.utils.SystemCheck.PingableManager;
import edu.greenblitz.utils.SystemCheck.SystemCheck;

public class PigeonGyro extends PigeonIMU implements IGyro, IPingable {
	double yawOffset = 0.0;
	double pitchOffset = 0.0;
	double rollOffset = 0.0;
	public PigeonGyro(int id) {
		super(id);

		PingableManager.getInstance().add(this);
	}
	
	/**
	 * the offset sets himself the current angle + the offset
	 * because idk but we do it like this
	 * <p>
	 * ALL IN RADIANS
	 */
	
	
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

	@Override
	public double getYaw() {
		return Math.IEEEremainder((Math.toRadians(super.getYaw()) - yawOffset), 2 * Math.PI);
	}
	
	@Override
	public double getPitch() {
		return ((Math.toRadians(super.getPitch()) - pitchOffset)%( 2 * Math.PI));
	}
	
	@Override
	public double getRoll() {
		return ((Math.toRadians(super.getRoll()) - rollOffset)%(2* Math.PI));
	}


	@Override
	public boolean isConnected() {
		return super.getFirmwareVersion() != -1; //todo check
	}

	@Override
	public String deviceName() {
		return "Pigeon 1 - " + this.getDeviceID();
	}
}
