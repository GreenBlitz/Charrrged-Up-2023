package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;

public class Battery extends GBSubsystem {
	
	private static final double minVoltage = RobotMap.General.SystemCheckConstants.MIN_VOLTAGE_BATTERY;
	private static Battery instance;

	private static PowerDistribution pdp = new PowerDistribution(20,PowerDistribution.ModuleType.kRev);

	private Battery() {
	}
	
	public static Battery getInstance() {
		init();
		return instance;
	}

	public static void init(){
		if (instance == null) {
			instance = new Battery();
		}
	}

	public double getCurrentUsage (){
		double totalCurrent = 0;
		for (int i = 0; i < pdp.getNumChannels(); i++) {
			totalCurrent += pdp.getCurrent(i);
		}
		return totalCurrent;
	}

	public double getCurrentVoltage() {
		return RobotController.getBatteryVoltage();
	}

	public double getMinVoltage() {
		return minVoltage;
	}

}
