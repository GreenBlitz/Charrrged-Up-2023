package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;

import edu.greenblitz.tobyDetermined.RobotMap;

public class ElbowFactory {
    public static IElbow create(){
        switch (RobotMap.ROBOT_TYPE){
            case FRANKENSTEIN:
                return new NeoElbow();
            case REPLAY:
                return new IElbow() {};
            case SIMULATION:
            default:
                return new SimulationElbow();
        }
    }
}
