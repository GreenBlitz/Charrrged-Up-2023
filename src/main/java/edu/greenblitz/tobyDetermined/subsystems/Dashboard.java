package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.PigeonGyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Dashboard extends GBSubsystem {

	private static Dashboard instance;
	long lastRead = System.currentTimeMillis();

	public static Dashboard getInstance() {
		if (instance == null) {
			init();
			SmartDashboard.putBoolean("dashboard initialized via getinstance", true);
		}
		return instance;
	}

	public static void init(){
		instance = new Dashboard();
	}



	@Override
	public void periodic() {
		SmartDashboard.putNumber("pigeon angle Yaw", Math.toDegrees(SwerveChassis.getInstance().getPigeonGyro().getYaw()));
		SmartDashboard.putNumber("pigeon angle Roll", SwerveChassis.getInstance().getPigeonGyro().getRoll());
		SmartDashboard.putNumber("pigeon angle Pitch", SwerveChassis.getInstance().getPigeonGyro().getPitch());
		lastRead = System.currentTimeMillis();
		SmartDashboard.putNumber("FR-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_RIGHT)), 360));
		SmartDashboard.putNumber("FL-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_LEFT)), 360));
		SmartDashboard.putNumber("BR-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_RIGHT)), 360));
		SmartDashboard.putNumber("BL-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_LEFT)), 360));
		SmartDashboard.putNumber("FR-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.FRONT_RIGHT));
		SmartDashboard.putNumber("FL-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.FRONT_LEFT));
		SmartDashboard.putNumber("BR-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.BACK_RIGHT));
		SmartDashboard.putNumber("BL-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.BACK_LEFT));
		SmartDashboard.putNumber("pressedticks", OI.getInstance().countB);

		SmartDashboard.putNumber("speed",SwerveChassis.getInstance().getModuleState(SwerveChassis.Module.FRONT_RIGHT).speedMetersPerSecond);
		double sum = 0;
		for (SwerveChassis.Module module : SwerveChassis.Module.values()) {
			sum += SwerveChassis.getInstance().getModuleAngle(module);
		}
		SmartDashboard.putString("pose", SwerveChassis.getInstance().getRobotPose().toString());
		SmartDashboard.putNumber("yaw", Math.toDegrees(SwerveChassis.getInstance().getPigeonGyro().getYaw()));
		SmartDashboard.putNumber("pitch",Math.toDegrees(SwerveChassis.getInstance().getPigeonGyro().getPitch()));
		SmartDashboard.putNumber("roll",Math.toDegrees(SwerveChassis.getInstance().getPigeonGyro().getRoll()));
		
	}
}
