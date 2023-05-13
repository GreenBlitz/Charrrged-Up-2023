package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;

public class Battery extends GBSubsystem {
	
	private static final double minVoltage = RobotMap.General.SystemCheckConstants.MIN_VOLTAGE_BATTERY;
	private static Battery instance;

	private static PowerDistribution pdp = new PowerDistribution();
	private static PneumaticsControlModule pcm = new PneumaticsControlModule(RobotMap.Pneumatics.PneumaticsController.ID);

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
		return  pdp.getTotalCurrent() ;
	}

	public double getCurrentVoltage() {
		return RobotController.getBatteryVoltage();
	}

	public double getMinVoltage() {
		return minVoltage;
	}

}
