package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.GyroIOs;

import edu.greenblitz.tobyDetermined.RobotMap;

public class Gyro {

    private GyroIO io;
    private final GyroIOInputsAutoLogged inputs = new GyroIOInputsAutoLogged();

    public Gyro() {
        switch (RobotMap.ROBOT_TYPE) {
            case Frankenstein:
            case pegaSwerve:
                io = new RealGyro();
                break;
            case SIMULATION:
                io = new SimGyro();
                break;
            case REPLAY:
            default:
                io = new GyroIO() {};
        }
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
