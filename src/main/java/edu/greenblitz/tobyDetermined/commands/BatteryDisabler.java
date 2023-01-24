package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class BatteryDisabler extends GBCommand {
	
	private static final int LEN_OF_AVG = 50;
	private Battery battery;
	private LinearFilter voltageFilter;
	
	
	public BatteryDisabler() {
		voltageFilter = LinearFilter.movingAverage(LEN_OF_AVG);
		battery = Battery.getInstance();
		require(battery);
	}
	
	@Override
	public void initialize() {
		for (int i = 0; i < LEN_OF_AVG; i++) {
			voltageFilter.calculate(battery.getCurrentVoltage());
		}
	}
	
	@Override
	public void execute() {
		SmartDashboard.putNumber("current battery voltage: ", Battery.getInstance().getCurrentVoltage());
		SmartDashboard.putNumber("current battery usage" , Battery.getInstance().getCurrentUsage());
		//

		double currentAverageVoltage;

		if(battery.getCurrentUsage() <= 1) {
			currentAverageVoltage = voltageFilter.calculate(battery.getCurrentVoltage());


			if (currentAverageVoltage <= battery.getMinVoltage()
					&& DriverStation.getMatchType() == DriverStation.MatchType.None) {
				CommandScheduler.getInstance().cancelAll();
				CommandScheduler.getInstance().disable();
				throw new java.lang.RuntimeException("Battery is low");
			}
		}
	}
}
