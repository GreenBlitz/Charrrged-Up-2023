package edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.IO;

import org.littletonrobotics.junction.AutoLog;

public interface IRotatingBelly {

    default void setPower(double power) {

    }

    default void stop() {

    }

    default void updateInputs(RotatingBellyInputs inputs) {

    }

    @AutoLog
    class RotatingBellyInputs {
        public double motorVoltage;
        public double motorCurrent;
        public boolean isSwitchPressed;
    }

}
