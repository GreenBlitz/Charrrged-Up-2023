package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Battery extends GBSubsystem {
	
	private static final double minVoltage = RobotMap.General.MIN_VOLTAGE_BATTERY;
	private static Battery instance;

	private static PowerDistribution pdp = new PowerDistribution();
	private static PneumaticsControlModule pcm = new PneumaticsControlModule(RobotMap.Pneumatics.PCM.PCM_ID);

	private DataLog log;
	private DoubleLogEntry voltagelog;
	private DoubleLogEntry usagelog;


	private Battery() {
		log = logger.getInstance().get_log();
		this.voltagelog = new DoubleLogEntry(this.log, "/Battery/LowLevel/Voltage");
		this.usagelog = new DoubleLogEntry(this.log, "/Battery/LowLevel/Usage");
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
		return  pdp.getTotalCurrent() + pcm.getCompressorCurrent();
	}

	public double getCurrentVoltage() {
		return RobotController.getBatteryVoltage();
	}

	public double getMinVoltage() {
		return minVoltage;
	}

	public void lowLevelLog(){
		voltagelog.append(getCurrentVoltage());
		usagelog.append(getCurrentUsage());
	}

}
