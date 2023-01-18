package edu.greenblitz.tobyDetermined.subsystems;

import edu.wpi.first.util.datalog.BooleanLogEntry;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.util.datalog.StringLogEntry;
import edu.wpi.first.wpilibj.DataLogManager;

import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;;


public class logger extends GBSubsystem {

    private static logger instance;
    private final DataLog log;

    private logger() {
        DataLogManager.start();
        log = DataLogManager.getLog();

	}
	
	public static logger getInstance() {
		if (instance == null) {
			instance = new logger();
		}
		return instance;
	} 
    
    public DataLog get_log(){
        return log;
    }

    @Override
    public void periodic(){
        SwerveChassis.getInstance().hightLevelLog();
        SwerveChassis.getInstance().lowLevelLog();
    }
}
