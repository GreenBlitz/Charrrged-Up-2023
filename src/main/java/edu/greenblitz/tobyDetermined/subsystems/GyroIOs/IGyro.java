package edu.greenblitz.tobyDetermined.subsystems.GyroIOs;

import edu.greenblitz.tobyDetermined.subsystems.swerve.inputs.GyroInputsAutoLogged;

public interface IGyro {

    void setYaw(double yaw);
    void setPitch(double pitch);
    void setRoll(double roll);

    double getYaw();
    double getPitch();
    double getRoll();


    void updateInputs(GyroInputsAutoLogged inputs);


}
