package edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis;

import edu.wpi.first.math.geometry.Pose2d;
import org.littletonrobotics.junction.AutoLog;

@AutoLog
public class SwerveChassisInputs {
    public Pose2d chassisPose;
    public boolean isVisionEnabled;
    public double numberOfDetectedAprilTag;
    public double xAxisSpeed;
    public double yAxisSpeed;
    public double omegaRadiansPerSecond;
    public double accelerationX;
    public double accelerationY;
}
