package edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis;

import edu.wpi.first.math.geometry.Pose2d;
import org.littletonrobotics.junction.AutoLog;

public interface ISwerveChassis {


    @AutoLog
    class SwerveChassisInputs {
        public Pose2d chassisPose;
        public boolean isVisionEnabled;
        public double numberOfDetectedAprilTag;
        public double xAxisSpeed;
        public double yAxisSpeed;
        public double omegaRadiansPerSecond;
    }

    void updateInputs(SwerveChassisInputsAutoLogged inputs);

}
