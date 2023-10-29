package edu.greenblitz.tobyDetermined.subsystems.DriveTrain.IO.GyroIOs;

import edu.greenblitz.tobyDetermined.RobotMap;

public class GyroFactory {

    public static IGyroIO create(){
        switch (RobotMap.ROBOT_TYPE) {
            case FRANKENSTEIN:
            case PEGA_SWERVE:
                return new RealGyro();
            case SIMULATION:
                return new SimulationGyro();
            case REPLAY:
            default:
                return new IGyroIO() {};
        }
    }

}
