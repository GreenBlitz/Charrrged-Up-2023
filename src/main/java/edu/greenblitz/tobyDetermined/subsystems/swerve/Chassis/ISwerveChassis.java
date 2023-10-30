package edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis;

import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassisInputsAutoLogged;
import edu.wpi.first.math.geometry.Pose2d;
import org.littletonrobotics.junction.AutoLog;

public interface ISwerveChassis {


    void updateInputs(SwerveChassisInputsAutoLogged inputs);

}
