package edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.rotation;

import org.littletonrobotics.junction.AutoLog;

public interface IRotatingBelly {

    void setPower(double power);
    void stop();

    void updateInputs(RotatingBellyInputs inputs);



}
