package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard extends GBSubsystem {

    private static Dashboard instance;
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
	private Dashboard(){
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

        SmartDashboard.putString("pose", SwerveChassis.getInstance().getRobotPose().toString());
		
		SmartDashboard.putNumber("grid pos id", Grid.getInstance().getSelectedPositionID());
		SmartDashboard.putString("grid pos", Grid.getInstance().getSelectedPosition().toString());
    }
}
