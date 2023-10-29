package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.GyroIOs;

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
        public double yaw;
        public double pitch;
        public double roll;
    }

    default void updateInputs(GyroIOInputsAutoLogged inputs) {
    }


}
