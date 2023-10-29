package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

import com.revrobotics.CANSparkMax;
import org.littletonrobotics.junction.AutoLog;

public interface IExtender {

    default void setPower(double power) {
    }

    default void setVoltage(double voltage) {
    }

    default void enableSoftLimit(boolean isEnabled, CANSparkMax.SoftLimitDirection direction){

    }

    default void setPosition (double position) {
    }
    default void setIdleMode (CANSparkMax.IdleMode idleMode) {
    }

    default void enableSoftLimit(CANSparkMax.SoftLimitDirection direction, boolean isEnabled) {
    }

    default void enableBackSwitchLimit(boolean enable) {
    }

    @AutoLog
    class ExtenderInputs{
        public double appliedOutput;
        public double outputCurrent;
        public double position;
        public double velocity;
        public boolean reverseLimitSwitchPressed;

        public double kP;
        public double kI;
        public double kD;
    }

    default void updateInputs(ExtenderInputs inputs){

    }


}
