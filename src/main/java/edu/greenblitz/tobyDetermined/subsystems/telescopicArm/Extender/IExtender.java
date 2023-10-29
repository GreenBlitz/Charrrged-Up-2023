package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

import com.revrobotics.CANSparkMax;
import org.littletonrobotics.junction.AutoLog;

public interface IExtender {

     void setPower(double power);

     void setVoltage(double voltage);
     void enableSoftLimit(boolean isEnabled, CANSparkMax.SoftLimitDirection direction);

     void setPosition (double position);
     void setIdleMode (CANSparkMax.IdleMode idleMode);

     void enableSoftLimit(CANSparkMax.SoftLimitDirection direction, boolean isEnabled);

     void enableBackSwitchLimit(boolean enable);
     void updateInputs(ExtenderInputs inputs);


}
