package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

import org.littletonrobotics.junction.AutoLog;

@AutoLog
public class ExtenderInputs {
    public double appliedOutput;
    public double outputCurrent;
    public double position;
    public double velocity;
    public boolean reverseLimitSwitchPressed;

    public double kP;
    public double kI;
    public double kD;

    public double tolerance;
    public String idleMode;
}
