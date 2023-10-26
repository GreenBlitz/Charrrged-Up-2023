package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO;

import edu.wpi.first.math.geometry.Pose2d;
import org.littletonrobotics.junction.AutoLog;

public interface ISwerveChassisIO {


    @AutoLog
    class SwerveChassisInputs {
        public Pose2d chassisPose;
        public boolean isVisionEnabled;
        public double numberOfDetectedAprilTag;
        public double xAxisSpeed;
        public double yAxisSpeed;
        public double omegaRadiansPerSecond;
    }

    default void updateInputs(SwerveChassisInputsAutoLogged inputs){

    }

}
