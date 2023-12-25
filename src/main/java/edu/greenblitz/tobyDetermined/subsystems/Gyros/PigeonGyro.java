//package edu.greenblitz.tobyDetermined.subsystems.Gyros;
//
////import com.ctre.phoenix.sensors.PigeonIMU;
//
//public class PigeonGyro implements IGyro {
//
//	private PigeonIMU pigeonIMU;
//	double yawOffset = 0.0;
//	double pitchOffset = 0.0;
//	double rollOffset = 0.0;
//
//	private GyroInputsAutoLogged lastInputs = new GyroInputsAutoLogged();
//	public PigeonGyro(int id) {
//		this.pigeonIMU = new PigeonIMU(id);
//	}
//
//	/**
//	 * the offset sets himself the current angle + the offset
//	 * because idk but we do it like this
//	 * <p>
//	 * ALL IN RADIANS
//	 */
//
//
//	@Override
//	public void setYaw(double yaw) {
//		yawOffset += (yaw + getYaw());
//	}
//
//	@Override
//	public void setPitch(double pitch) {
//		pitchOffset += (pitch + getPitch());
//	}
//
//	@Override
//	public void setRoll(double roll) {
//		rollOffset += (roll + getRoll());
//	}
//
//	@Override
//	public double getYaw() {
//		return lastInputs.yaw;
//	}
//
//	@Override
//	public double getPitch() {
//		return lastInputs.pitch;
//	}
//
//	@Override
//	public double getRoll() {
//		return lastInputs.roll;
//	}
//
//	@Override
//	public void updateInputs(GyroInputsAutoLogged inputs) {
//		inputs.yaw = -((Math.toRadians(pigeonIMU.getYaw()) - yawOffset)%(2* Math.PI));
//		inputs.pitch = ((Math.toRadians(pigeonIMU.getPitch()) - pitchOffset)%( 2 * Math.PI));
//		inputs.roll = ((Math.toRadians(pigeonIMU.getRoll()) - rollOffset)%(2* Math.PI));
//
//		lastInputs = inputs;
//	}
//}
