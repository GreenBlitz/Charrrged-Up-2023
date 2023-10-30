package edu.greenblitz.tobyDetermined.subsystems.swerve;

public class SwerveModuleConfigObject {

    public int angleMotorID;
    public int linearMotorID;
    public int AbsoluteEncoderID;
    public boolean linInverted;
    public double encoderOffset;

    public SwerveModuleConfigObject(int angleMotorID, int linearMotorID, int AbsoluteEncoderID,double encoderOffset, boolean linInverted) {
        this.angleMotorID = angleMotorID;
        this.linearMotorID = linearMotorID;
        this.AbsoluteEncoderID = AbsoluteEncoderID;
        this.linInverted = linInverted;
        this.encoderOffset = encoderOffset;
    }
    public SwerveModuleConfigObject(int angleMotorID, int linearMotorID, int AbsoluteEncoderID,boolean linInverted) {
        this(
                angleMotorID,
                linearMotorID,
                AbsoluteEncoderID,
                0,
                linInverted
        );
    }



}
