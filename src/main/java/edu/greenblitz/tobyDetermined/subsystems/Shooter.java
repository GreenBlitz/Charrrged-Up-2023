package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class Shooter extends GBSubsystem{


    private CANSparkMax motor;

    private static Shooter instance;

    public Shooter (){
        motor = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);

    }
    public static Shooter getInstance(){
        if(instance == null){
            instance = new Shooter();
        }
        return instance;
    }

    public void setPower(double power){
        motor.set(power);
    }

    public void stop(){
        motor.set(0);
    }

}
