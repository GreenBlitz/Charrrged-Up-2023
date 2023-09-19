package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorSensorV3;

public class Shooter extends GBSubsystem {
    private static Shooter instance;
    private CANSparkMax motor;
    private ColorSensorV3 cs;
    private  boolean isBall;

    private Shooter() {
        this.motor = new CANSparkMax();
        this.cs=new ColorSensorV3();

    }
    public static Shooter getInstance(){
        if (instance==null){
            instance=new Shooter();
        }
        return instance;
    }
    public void SetPower(double speed){
        motor.set(speed);
    }
    public boolean isBallEnemy (){
        if (cs.getBlue()>110&&cs.getRed()>225){
            return true;
        }
    }

}
