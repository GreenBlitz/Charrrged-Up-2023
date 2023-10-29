package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.GyroIOs;

import edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.inputs.GyroInputsAutoLogged;
import org.littletonrobotics.junction.AutoLog;

public interface IGyroIO {

    default void setYaw(double yaw) {
    }
    default void setPitch(double pitch) {
    }
    default void setRoll(double roll) {
    }

    @AutoLog
    class GyroIOInputs {

    }

    default void updateInputs(GyroInputsAutoLogged inputs) {
    }


}
