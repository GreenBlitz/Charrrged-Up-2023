package edu.greenblitz.utils;

import edu.wpi.first.wpilibj.AnalogInput;

public class PressureSensor extends AnalogInput {

	public PressureSensor(int channel) {
		super(channel);
	}

	public double getPressure() {
		return Math.max(0, (250 * getVoltage() / 5) - 29/*25 (Through testing, subtracted 4 from the original formula)*/);
	}

	public double getAveragePressure() {
		return Math.max(0, (250 * getAverageVoltage() / 5) - 29/*25 (Through testing, subtracted 4 from the original formula)*/);
	}
}
