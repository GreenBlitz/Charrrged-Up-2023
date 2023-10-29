package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

import edu.greenblitz.tobyDetermined.RobotMap;

public class ExtenderFactory {

    public static IExtender create (){
        switch (RobotMap.ROBOT_TYPE){
            case FRANKENSTEIN:
                return new NeoExtender();
            case SIMULATION:
                return new SimulationExtender();
            default:
                return new IExtender(){};
        }
    }
}
