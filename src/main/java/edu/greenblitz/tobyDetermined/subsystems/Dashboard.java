package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.Robot;
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
		if(Robot.isReal()){
		SmartDashboard.putNumber("module speed", SwerveChassis.getInstance().getModuleState(SwerveChassis.Module.FRONT_RIGHT).speedMetersPerSecond);
		SmartDashboard.putNumber("angular speed", (SwerveChassis.getInstance().getPigeonGyro().getYaw() - angularState) / ((System.currentTimeMillis() - lastRead) / 1000.0));
		SmartDashboard.putNumber("pigeon angle", SwerveChassis.getInstance().getPigeonGyro().getYaw());
		lastRead = System.currentTimeMillis();
		SmartDashboard.putNumber("FR-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_RIGHT)), 360));
		SmartDashboard.putNumber("FL-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_LEFT)), 360));
		SmartDashboard.putNumber("BR-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_RIGHT)), 360));
		SmartDashboard.putNumber("BL-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_LEFT)), 360));
		SmartDashboard.putNumber("FR-angle-lamprey", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.FRONT_RIGHT));
		SmartDashboard.putNumber("FL-angle-lamprey", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.FRONT_LEFT));
		SmartDashboard.putNumber("BR-angle-lamprey", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.BACK_RIGHT));
		SmartDashboard.putNumber("BL-angle-lamprey", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.BACK_LEFT));
		}
		double sum = 0;
		for (SwerveChassis.Module module : SwerveChassis.Module.values()) {
			sum += SwerveChassis.getInstance().getModuleAngle(module);
		}
		SmartDashboard.putBoolean("an azimuth encoder is nan", Double.isNaN(sum));
		SmartDashboard.putString("pose", SwerveChassis.getInstance().getRobotPose().toString());
	}
}
