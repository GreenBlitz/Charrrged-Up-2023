package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;

import com.revrobotics.CANSparkMax;
import org.littletonrobotics.junction.AutoLog;

public interface IElbow {

    default void setPosition(double position){}
    default void setPower(double power){}


    default void setAngleRadiansByPID(double goalAngle, double feedForward){

    }

    default void setVoltage(double voltage){}
    default void setIdleMode(CANSparkMax.IdleMode idleMode){}

    default void setSoftLimit(CANSparkMax.SoftLimitDirection direction, double limit){}



    default void updateInputs(ElbowInputsAutoLogged inputs){

    }



}
