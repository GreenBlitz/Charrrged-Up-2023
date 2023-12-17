package edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.rotation;

import edu.greenblitz.tobyDetermined.RobotMap;

public class RotatingBellyFactory {


    public static IRotatingBelly create(){
        switch (RobotMap.ROBOT_TYPE){
            case FRANKENSTEIN:
//                return new TalonSRXRotatingBelly();
            case REPLAY:
            default:
                return new ReplayRotationBelly();

        }
    }





}
