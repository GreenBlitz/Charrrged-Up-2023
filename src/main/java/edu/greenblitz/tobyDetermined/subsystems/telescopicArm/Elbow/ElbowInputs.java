package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;

import org.littletonrobotics.junction.AutoLog;

@AutoLog
public class ElbowInputs {
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
