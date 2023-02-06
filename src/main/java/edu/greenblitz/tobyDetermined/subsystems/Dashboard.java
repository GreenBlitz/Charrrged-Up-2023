package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.swerve.RotateAllWheelsToAngle;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.PigeonGyro;
import edu.wpi.first.math.util.Units;
import edu.greenblitz.utils.PitchRollAdder;
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
		SmartDashboard.putBoolean("has target", Limelight.getInstance().visionPoseEstimator().isPresent());
	}
}
