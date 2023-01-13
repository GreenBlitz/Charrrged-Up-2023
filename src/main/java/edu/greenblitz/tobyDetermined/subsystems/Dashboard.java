package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Dashboard extends GBSubsystem {
	
	private static Dashboard instance;
	double angularState = 0;
	long lastRead = System.currentTimeMillis();

	public static Dashboard init() {
		if (instance == null) {
			instance = new Dashboard();
		}
		return instance;
	}
	
	@Override
	public void periodic() {

		
	}
}
