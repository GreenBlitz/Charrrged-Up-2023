package edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.rotation;

import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.rotation.RotatingBellyInputsAutoLogged;

public class RotatingBelly extends GBSubsystem {


    private static RotatingBelly instance;

    private IRotatingBelly rotatingBelly;
    private final RotatingBellyInputsAutoLogged rotatingBellyInputs = new RotatingBellyInputsAutoLogged();


//    private BellyGameObjectSensor colorSensor;

    private RotatingBelly (){
        rotatingBelly = RotatingBellyFactory.create();
    }
    public void setPower(double power){
        rotatingBelly.setPower(power);
    }

    public static RotatingBelly getInstance (){
        init();
        return instance;
    }

    public static void init(){
        if(instance == null){
            instance = new RotatingBelly();
        }
    }

    public void stop (){
        rotatingBelly.stop();
    }
    public boolean isLimitSwitchPressed(){
        return rotatingBellyInputs.isSwitchPressed;
    }

    @Override
    public void periodic() {
        rotatingBelly.updateInputs(rotatingBellyInputs);
    }
}
