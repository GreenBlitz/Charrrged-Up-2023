package edu.greenblitz.tobyDetermined.subsystems.Gyros;

import edu.greenblitz.tobyDetermined.RobotMap;

public class GyroFactory {

    public static IGyro create(){
        switch (RobotMap.ROBOT_TYPE) {
            case FRANKENSTEIN:
            case PEGA_SWERVE:
                return new NavXGyro();
            case REPLAY:
                return new ReplayGyro();
            case SIMULATION:
            default:
                return new SimulationGyro();
        }
    }

}
