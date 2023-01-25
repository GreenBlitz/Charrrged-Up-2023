package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.swerve.RotateAllWheelsToAngle;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.PigeonGyro;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Dashboard extends GBSubsystem {

	private static Dashboard instance;
	double angularState = 0;
	long lastRead = System.currentTimeMillis();

	public static Dashboard init() {
		if (instance == null) {
			instance = new Dashboard();
			SmartDashboard.putNumber("a",0);
		}
		return instance;
	}



	@Override
	public void periodic() {
		SmartDashboard.putNumber("FR-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_RIGHT)), 360));
		SmartDashboard.putNumber("FL-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_LEFT)), 360));
		SmartDashboard.putNumber("BR-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_RIGHT)), 360));
		SmartDashboard.putNumber("BL-angle-neo", Math.IEEEremainder(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_LEFT)), 360));
		SmartDashboard.putNumber("y", SwerveChassis.getInstance().getRobotPose().getY());
		SmartDashboard.putNumber("x", SwerveChassis.getInstance().getRobotPose().getX());
		SmartDashboard.putNumber("angle", SwerveChassis.getInstance().getRobotPose().getRotation().getDegrees());
		SmartDashboard.putNumber("pigeon angle", Units.radiansToDegrees(SwerveChassis.getInstance().getPigeonGyro().getYaw()));
		
		SmartDashboard.putNumber("chassis speed x", SwerveChassis.getInstance().getChassisSpeeds().vxMetersPerSecond);
		SmartDashboard.putNumber("FR-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.FRONT_RIGHT));
		SmartDashboard.putNumber("FL-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.FRONT_LEFT));
		SmartDashboard.putNumber("BR-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.BACK_RIGHT));
		SmartDashboard.putNumber("BL-angle-absolute", SwerveChassis.getInstance().getModuleAbsoluteEncoderValue(SwerveChassis.Module.BACK_LEFT));
		SmartDashboard.putNumber("kp", RobotMap.Swerve.translationPID.getKp())	;
		RobotMap.Swerve.translationPID.setKp(SmartDashboard.getNumber("a",2.9));
		SmartDashboard.putNumber("speed",SwerveChassis.getInstance().getModuleState(SwerveChassis.Module.FRONT_RIGHT).speedMetersPerSecond);
		SmartDashboard.putString("pose", SwerveChassis.getInstance().getRobotPose().toString());
		
	}
}
