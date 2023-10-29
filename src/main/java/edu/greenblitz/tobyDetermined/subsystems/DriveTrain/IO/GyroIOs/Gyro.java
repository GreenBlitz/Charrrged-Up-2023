package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.GyroIOs;

import edu.greenblitz.tobyDetermined.RobotMap;

public class Gyro {

    private IGyroIO io;
    private final GyroIOInputsAutoLogged inputs = new GyroIOInputsAutoLogged();

    public Gyro() {
        io = GyroFactory.create();
    }

    public double getYaw() {
        return inputs.yaw;
    }
    public double getPitch() {
        return inputs.pitch;
    }
    public double getRoll() {
        return inputs.roll;
    }

    public void setYaw(double yaw) {
        io.setYaw(yaw);
    }
    public void setPitch(double pitch) {
        io.setPitch(pitch);
    }
    public void setRoll(double roll) {
        io.setRoll(roll);
    }

    public void periodic(){
        io.updateInputs(inputs);
    }



}
