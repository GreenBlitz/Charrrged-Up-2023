package edu.greenblitz.tobyDetermined.subsystems;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;

public class Accelerometer extends GBSubsystem {

	private static Accelerometer instance;
	private BuiltInAccelerometer accelerometer;

	private Accelerometer()
	{
		accelerometer = new BuiltInAccelerometer();
	}

	public static Accelerometer getInstance() {
		if(instance == null) {
			instance = new Accelerometer();
		}
		return instance;
	}

	public double getX()
	{
		return accelerometer.getX();
	}
	public double getY()
	{
		return accelerometer.getY();
	}
	public double getZ()
	{
		return accelerometer.getZ();
	}


}
