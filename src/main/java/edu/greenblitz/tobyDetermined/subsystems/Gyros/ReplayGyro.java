package edu.greenblitz.tobyDetermined.subsystems.Gyros;

public class ReplayGyro implements IGyro{

    private GyroInputsAutoLogged lastInputs = new GyroInputsAutoLogged();
    @Override
    public void setYaw(double yaw) {

    }

    @Override
    public void setPitch(double pitch) {

    }

    @Override
    public void setRoll(double roll) {

    }

    @Override
    public double getYaw() {
        return lastInputs.yaw;
    }

    @Override
    public double getPitch() {
        return lastInputs.pitch;
    }

    @Override
    public double getRoll() {
        return lastInputs.roll;
    }

    @Override
    public void updateInputs(GyroInputsAutoLogged inputs) {
        lastInputs = inputs;
    }
}
