package edu.greenblitz.tobyDetermined.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class MyShooter extends GBSubsystem{
    private CANSparkMax upperMotor;
    private TalonSRX middleMotor;
    private TalonSRX lowerMottor;
    private static MyShooter instance;

    private MyShooter(){
        upperMotor = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
        middleMotor = new TalonSRX(3);
        lowerMottor = new TalonSRX(5);
    }

    public static MyShooter getInstace() {
        if (instance == null){
            instance = new MyShooter();
        }
        return instance;
    }
    public void setUpperPower(double Speed){
        upperMotor.set(Speed);
    }
    public void setMiddlePower(double Speed){
        middleMotor.set(TalonSRXControlMode.PercentOutput, Speed);
    }
    public void setLowerPower(double Speed){
        middleMotor.set(TalonSRXControlMode.PercentOutput, Speed);
    }


}
