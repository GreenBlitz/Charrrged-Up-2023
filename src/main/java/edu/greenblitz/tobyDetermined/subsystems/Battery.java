package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Battery extends GBSubsystem {
	
	private static final double minVoltage = RobotMap.General.minVoltageBattery;
	private static Battery instance;

	private static PowerDistribution pdp = new PowerDistribution();

	private Battery() {
	}
	
	public static Battery getInstance() {
		if (instance == null) {
			init();
			SmartDashboard.putBoolean("battery initialized via getinstance", true);
		}
		return instance;
	}

	public static void init(){
		instance = new Battery();
	}

	public double getCurrentUsage (){
		return  pdp.getTotalCurrent();
	}

	public double getCurrentVoltage() {
		return RobotController.getBatteryVoltage();
	}

	public double getMinVoltage() {
		return minVoltage;
	}

}
