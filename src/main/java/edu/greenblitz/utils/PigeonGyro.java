package edu.greenblitz.utils;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.sensors.PigeonIMU;

public class PigeonGyro extends PigeonIMU {
	//fixme noam - reminder for myself. if pigeon not working problem is prob here;
	
	double yawOffset = 0.0;
	double pitchOffset = 0.0;
	double rollOffset = 0.0;
	public PigeonGyro(int id) {
		super(id);
	}
	
	/**
	 * the offset sets himself the current angle + the offset
	 * because idk but we do it like this
	 * <p>
	 * ALL IN RADIANS
	 */
	
	
	@Override
	public ErrorCode setYaw(double yaw) {
		yawOffset += (yaw + getYaw());
		return ErrorCode.valueOf(0);
	}
	
	public void setYawOffset(double offset) {
		yawOffset += offset;
	}
	
	public void setPitchOffset(double offset) {
		pitchOffset += offset;
	}
	
	public void setRollOffset(double offset) {
		rollOffset += offset;
	}
	
	@Override
	public double getYaw() {
		return Math.IEEEremainder((Math.toRadians(super.getYaw()) - yawOffset), 2 * Math.PI);
	}
	
	@Override
	public double getPitch() {
		return Math.IEEEremainder((Math.toRadians(super.getPitch()) - pitchOffset), 2 * Math.PI);
	}
	
	@Override
	public double getRoll() {
		return Math.IEEEremainder((Math.toRadians(super.getRoll()) - rollOffset), 2 * Math.PI);
	}
	
	
}
