package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO;

import org.littletonrobotics.junction.AutoLog;

public interface IModuleIO {

    default void setLinearVelocity(double speed) {
    }

    default void rotateToAngle(double angleInRadians) {
    }

    default void setLinearVoltage(double voltage) {
    }

    default void setAngularVoltage(double voltage) {
    }

    default void setLinearIdleModeBrake(boolean isBrake) {
    }

    default void setAngularIdleModeBrake(boolean isBrake) {
    }

    default void resetAngle(double angleInRads) {
    }


    default void stop() {
    }

    @AutoLog
    class IModuleIOInputs {
        public double linearVelocity;
        public double angularVelocity;

        public double linearVoltage;
        public double angularVoltage;

        public double linearCurrent;
        public double angularCurrent;

        public double angularPositionInRads;
        public double linearMetersPassed;

        public double absoluteEncoderPosition;

        public boolean isAbsoluteEncoderConnected;

    }

    default void updateInputs(IModuleIOInputs inputs) {
    }




}
