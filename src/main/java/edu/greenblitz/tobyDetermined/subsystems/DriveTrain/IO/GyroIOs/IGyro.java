package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.GyroIOs;

import edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.inputs.GyroInputsAutoLogged;
import org.littletonrobotics.junction.AutoLog;

public interface IGyro {

    void setYaw(double yaw);
    void setPitch(double pitch);
    void setRoll(double roll);

    double getYaw();
    double getPitch();
    double getRoll();


    void updateInputs(GyroInputsAutoLogged inputs);


}
