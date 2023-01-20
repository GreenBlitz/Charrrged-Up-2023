package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.FMSUtils;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Battery extends GBSubsystem {
	
	private static final double minVoltage = RobotMap.General.MIN_VOLTAGE_BATTERY;
	private static Battery instance;

	private LinearFilter voltageFilter;

	private static final int LEN_OF_AVG = 50;



	private static PowerDistribution pdp = new PowerDistribution();

	private Battery() {
		voltageFilter = LinearFilter.movingAverage(LEN_OF_AVG);
		for (int i = 0; i < LEN_OF_AVG; i++) {
			voltageFilter.calculate(this.getCurrentVoltage());
		}
		SmartDashboard.putBoolean("battery low",false);

	}
	@Override
	public void periodic() {
		SmartDashboard.putNumber("current battery voltage: ", Battery.getInstance().getCurrentVoltage());
		SmartDashboard.putNumber("current battery usage" , Battery.getInstance().getCurrentUsage());

		double currentAverageVoltage;

		if(isCurrentUsageLow()) {
			currentAverageVoltage = voltageFilter.calculate(this.getCurrentVoltage());


			if (isVoltageLow() && !FMSUtils.isRealMatch()) {
				SmartDashboard.putBoolean("battery low",true);
				//add led signal for low battery

				disableRobot(); //delete on competition
			}
		}

	}

	public boolean isVoltageLow (){
		return getCurrentVoltage() < minVoltage;
	}
	public boolean isCurrentUsageLow (){
		return getCurrentUsage() < RobotMap.General.HIGH_CURRENT_USAGE;
	}

	public static Battery getInstance() {
		if (instance == null) {
			instance = new Battery();
		}
		return instance;
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

	public void disableRobot (){
		CommandScheduler.getInstance().cancelAll();
		CommandScheduler.getInstance().disable();
		throw new RuntimeException("battery low");
	}
}
