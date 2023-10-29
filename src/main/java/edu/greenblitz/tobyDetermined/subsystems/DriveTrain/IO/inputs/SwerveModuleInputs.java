package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.inputs;


import org.littletonrobotics.junction.AutoLog;

@AutoLog
public class SwerveModuleInputs {
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
