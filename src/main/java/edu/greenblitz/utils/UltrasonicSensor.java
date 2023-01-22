package edu.greenblitz.utils;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UltrasonicSensor {
	private final DigitalOutput trigger;
	private final DigitalInput input;
	private Timer timer;
	private int counter = 0;
	
	public UltrasonicSensor(int triggerID, int inputID) {
		this.trigger = new DigitalOutput(triggerID);
		this.input = new DigitalInput(inputID);
		timer = new Timer();
	}
	
	public double measure(){
		timer.reset();
		timer.start();
		trigger.pulse(10.0/1000000); //10 microseconds
//		while (!input.get()){
//			counter++;
//			SmartDashboard.putNumber("counter In ultrasonic", counter);
//		}
//		timer.stop();
//		return timer.get();
		while (input.get()){
			counter++;
		}
		return counter;
	}

	public boolean getInput(){
		return input.get();
	}
}
