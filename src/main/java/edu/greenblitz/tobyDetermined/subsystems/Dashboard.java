package edu.greenblitz.tobyDetermined.subsystems;

import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.PitchRollAdder;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Dashboard extends GBSubsystem {

    private static Dashboard instance;

    public static Dashboard init() {
        if (instance == null) {
            instance = new Dashboard();
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

        double sum = 0;
        for (SwerveChassis.Module module : SwerveChassis.Module.values()) {
            sum += SwerveChassis.getInstance().getModuleAngle(module);
        }
        SmartDashboard.putBoolean("an azimuth encoder is nan", Double.isNaN(sum));
        SmartDashboard.putString("pose", SwerveChassis.getInstance().getRobotPose().toString());
        SmartDashboard.putNumber("speed", SwerveChassis.getInstance().getModuleState(SwerveChassis.Module.FRONT_RIGHT).speedMetersPerSecond);
        SmartDashboard.putString("pose", SwerveChassis.getInstance().getRobotPose().toString());


        SmartDashboard.putNumber("yaw", Math.toDegrees(SwerveChassis.getInstance().getPigeonGyro().getYaw()));
        SmartDashboard.putNumber("pitch", Math.toDegrees(SwerveChassis.getInstance().getPigeonGyro().getPitch()));
        SmartDashboard.putNumber("roll", Math.toDegrees(SwerveChassis.getInstance().getPigeonGyro().getRoll()));

        SmartDashboard.putNumber("real pitch", Math.toDegrees(PitchRollAdder.add(SwerveChassis.getInstance().getPigeonGyro().getRoll(), SwerveChassis.getInstance().getPigeonGyro().getPitch())));
        SmartDashboard.putString("Detected Color", IntakeGameObjectSensor.getInstance().curObject.toString());
        SmartDashboard.putString("Detected Color", IntakeGameObjectSensor.getInstance().curObject.toString());
    }
}
