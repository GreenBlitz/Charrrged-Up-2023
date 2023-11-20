package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

import edu.greenblitz.tobyDetermined.RobotMap;

public class ExtenderFactory {

    public static IExtender create() {
        switch (RobotMap.ROBOT_TYPE) {
            case FRANKENSTEIN:
                return new NeoExtender();
            case REPLAY:
                return new ReplayExtender();
            case PEGA_SWERVE:
            case SIMULATION:
            default:
                return new SimulationExtender();
        }
    }
}
