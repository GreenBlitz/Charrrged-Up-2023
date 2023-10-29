package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.GyroIOs;

import com.kauailabs.navx.frc.AHRS;
import edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.inputs.GyroInputsAutoLogged;
import edu.wpi.first.wpilibj.SerialPort;

public class NavX implements IGyro {
	private AHRS gyro;
	private double yawOffset;
	private double pitchOffset;
	private double rollOffset;

	private GyroInputsAutoLogged lastInputs = new GyroInputsAutoLogged();

	public NavX(){
		this.gyro = new AHRS(SerialPort.Port.kMXP);

		yawOffset = 0;
		pitchOffset = 0;
		rollOffset = 0;
	}

	@Override
	public double getYaw() {
		return lastInputs.yaw;
	}

	@Override
	public double getPitch() {
		return lastInputs.pitch;
	}

	@Override
	public double getRoll() {
		return lastInputs.roll;
	}

	@Override
	public void setYaw(double yaw) {
		yawOffset += (yaw + getYaw());
	}

	@Override
	public void setPitch(double pitch) {
		pitchOffset += (pitch + getPitch());
	}

	@Override
	public void setRoll(double roll) {
		rollOffset += (roll + getRoll());
	}


	@Override
	public void updateInputs(GyroInputsAutoLogged inputs) {
		inputs.yaw =  -Math.IEEEremainder((Math.toRadians(gyro.getYaw()) - yawOffset), 2 * Math.PI);
		inputs.pitch = ((Math.toRadians(gyro.getPitch()) - pitchOffset)%( 2 * Math.PI));
		inputs.roll = ((Math.toRadians(gyro.getRoll()) - rollOffset)%(2* Math.PI));

		lastInputs = inputs;
	}
}
