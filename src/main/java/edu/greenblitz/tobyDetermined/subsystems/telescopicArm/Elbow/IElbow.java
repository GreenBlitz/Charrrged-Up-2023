package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;

import com.revrobotics.CANSparkMax;
import org.littletonrobotics.junction.AutoLog;

public interface IElbow {

    void setPosition(double position);

    void setPower(double power);

    void setAngleRadiansByPID(double goalAngle, double feedForward);

    void setVoltage(double voltage);

    void setIdleMode(CANSparkMax.IdleMode idleMode);

    void setSoftLimit(CANSparkMax.SoftLimitDirection direction, double limit);


    void updateInputs(ElbowInputs inputs);


}
