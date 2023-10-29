package edu.greenblitz.tobyDetermined.subsystems.RotatingBelly;

import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.IO.IRotatingBelly;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.IO.TalonSRXRotatingBelly;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.IO.RotatingBellyInputsAutoLogged;

public class RotatingBelly extends GBSubsystem {


    private static RotatingBelly instance;

    private IRotatingBelly io;
    private final RotatingBellyInputsAutoLogged inputs = new RotatingBellyInputsAutoLogged();


//    private BellyGameObjectSensor colorSensor;

    private RotatingBelly (){
        io = new TalonSRXRotatingBelly();
    }
    public void setPower(double power){
        io.setPower(power);
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
        io.stop();
    }
    public boolean isLimitSwitchPressed(){
        return inputs.isSwitchPressed;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
    }
}
