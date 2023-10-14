package edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.IO;

import org.littletonrobotics.junction.AutoLog;

public interface RotatingBellyIO {

    default void setPower(double power) {

    }

    default void stop() {

    }

    default void updateInputs(RotatingBellyInputs inputs) {

    }

    @AutoLog
    class RotatingBellyInputs {
        public double motorVoltage;
        public boolean isSwitchPressed;
    }

}
