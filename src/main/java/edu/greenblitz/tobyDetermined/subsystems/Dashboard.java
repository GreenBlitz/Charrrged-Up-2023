package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
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
//		SmartDashboard.putNumber(" Yaw", Math.toDegrees(SwerveChassis.getInstance().getPigeonGyro().getYaw()));
//		SmartDashboard.putNumber("Roll", SwerveChassis.getInstance().getPigeonGyro().getRoll());
//		SmartDashboard.putNumber("Pitch", SwerveChassis.getInstance().getPigeonGyro().getPitch());
//
//		SmartDashboard.putNumber("FR-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_RIGHT)), 360));
//		SmartDashboard.putNumber("FL-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_LEFT)), 360));
//		SmartDashboard.putNumber("BR-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_RIGHT)), 360));
//		SmartDashboard.putNumber("BL-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_LEFT)), 360));
//		SmartDashboard.putNumber("FR-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.FRONT_RIGHT));
//		SmartDashboard.putNumber("FL-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.FRONT_LEFT));
//		SmartDashboard.putNumber("BR-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.BACK_RIGHT));
//		SmartDashboard.putNumber("BL-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.BACK_LEFT));
//
//		SmartDashboard.putNumber("yaw", Math.toDegrees(SwerveChassis.getInstance().getPigeonGyro().getYaw()));
//		SmartDashboard.putNumber("pitch",Math.toDegrees(SwerveChassis.getInstance().getPigeonGyro().getPitch()));
//		SmartDashboard.putNumber("roll",Math.toDegrees(SwerveChassis.getInstance().getPigeonGyro().getRoll()));
//
//		SmartDashboard.putNumber("real pitch", Math.toDegrees(PitchRollAdder.add(SwerveChassis.getInstance().getPigeonGyro().getRoll(),SwerveChassis.getInstance().getPigeonGyro().getPitch())));
//		SmartDashboard.putString("Detected Color", IntakeGameObjectSensor.getInstance().curObject.toString());
//		SmartDashboard.putString("Detected Color", IntakeGameObjectSensor.getInstance().curObject.toString());
		SmartDashboard.putNumber("length",Grid.Location.values().length);
		SmartDashboard.putString("grid pos", Grid.getInstance().getPose().toString());
		SmartDashboard.putNumber("grid pos id", Grid.getInstance().getPose().ordinal());
	}
}
