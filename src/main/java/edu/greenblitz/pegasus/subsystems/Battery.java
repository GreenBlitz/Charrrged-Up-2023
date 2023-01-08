package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj.RobotController;

public class Battery extends GBSubsystem {

	private double currentVoltage;
	private static final double minVoltage = RobotMap.Pegasus.General.minVoltageBattery;
	private static Battery instance;


	private Battery (){}

	public static Battery getInstance(){
		if(instance == null){
			instance = new Battery();
		}
		return instance;
	}

	public double getCurrentVoltage() {
		return RobotController.getBatteryVoltage();
	}

	public double getMinVoltage(){
		return minVoltage;
	}
}
