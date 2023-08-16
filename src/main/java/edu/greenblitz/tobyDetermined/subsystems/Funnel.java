package edu.greenblitz.tobyDetermined.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;

public class Funnel extends GBSubsystem{



    private TalonSRX motor;

    private boolean inverted = true;

    private static Funnel instance;


    public Funnel(){
        motor = new TalonSRX(5); //todo find/calibrate
        motor.setInverted(true);

    }
    public static Funnel getInstance(){
        if(instance == null){
            instance = new Funnel();
        }
        return instance;
    }
    public boolean isInverted() {
        return inverted;
    }
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    public void setPower(double power){
        motor.set(TalonSRXControlMode.PercentOutput,power);
    }

    public void stop(){
        setPower(0);
    }
}
