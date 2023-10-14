package edu.greenblitz.tobyDetermined.subsystems.RotatingBelly;

import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.IO.RotatingBellyIOTalonSRX;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.IO.RotatingBellyInputsAutoLogged;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RotatingBelly extends GBSubsystem {


    private static RotatingBelly instance;

    private RotatingBellyIOTalonSRX io;
    private final RotatingBellyInputsAutoLogged inputs = new RotatingBellyInputsAutoLogged();


//    private BellyGameObjectSensor colorSensor;

    private RotatingBelly (){
        io = new RotatingBellyIOTalonSRX();
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
        SmartDashboard.putBoolean("is", inputs.isSwitchPressed);
    }
}
