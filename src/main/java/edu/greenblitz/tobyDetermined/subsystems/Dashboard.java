package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard extends GBSubsystem {

	private static Dashboard instance;
	public static Dashboard init() {

		if (instance == null) {
			instance = new Dashboard();
		}
		return instance;
	}

	private Dashboard(){
	}


	@Override
	public void periodic() {
		SmartDashboard.putNumber(" Yaw", Math.toDegrees(SwerveChassis.getInstance().getPigeonGyro().getYaw()));
		SmartDashboard.putNumber("Roll", SwerveChassis.getInstance().getPigeonGyro().getRoll());
		SmartDashboard.putNumber("Pitch", SwerveChassis.getInstance().getPigeonGyro().getPitch());

		SmartDashboard.putNumber("FR-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_RIGHT)), 360));
		SmartDashboard.putNumber("FL-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_LEFT)), 360));
		SmartDashboard.putNumber("BR-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_RIGHT)), 360));
		SmartDashboard.putNumber("BL-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_LEFT)), 360));
		SmartDashboard.putNumber("BL-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.BACK_LEFT));
		SmartDashboard.putNumber("FR-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.FRONT_RIGHT));
		SmartDashboard.putNumber("FL-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.FRONT_LEFT));
		SmartDashboard.putNumber("BR-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.BACK_RIGHT));

	}
}
