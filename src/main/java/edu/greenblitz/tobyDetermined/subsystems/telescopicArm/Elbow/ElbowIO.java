package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;

import com.revrobotics.CANSparkMax;
import org.littletonrobotics.junction.AutoLog;

public interface ElbowIO {

    default void setPosition(double position){}
    default void setPower(double power){}


    default void setAngleRadiansByPID(double goalAngle, double feedForward){

    }

    default void setVoltage(double voltage){}
    default void setIdleMode(CANSparkMax.IdleMode idleMode){}

    default void setSoftLimit(CANSparkMax.SoftLimitDirection direction, double limit){}

    @AutoLog
    class ElbowInputs{
        public double appliedOutput;
        public double outputCurrent;
        public double position;
        public double velocity;

        public double absoluteEncoderPosition;
        public double absoluteEncoderVelocity;

        public double kP;
        public double kI;
        public double kD;

        public boolean hasHitForwardLimit;
        public boolean hasHitBackwardsLimit;
    }

    default void updateInputs(ElbowInputs inputs){

    }



}
