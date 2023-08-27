package edu.greenblitz.tobyDetermined.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class MyShooter extends GBSubsystem{
    private CANSparkMax motor1;
    private TalonSRX motor2;
    private TalonSRX motor3;
    private static MyShooter instance;

    private MyShooter(){
        motor1 = new CANSparkMax(-1, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor2 = new TalonSRX(-1);
        motor3 = new TalonSRX(-1);
    }

    public static MyShooter getInstace() {
        if (instance == null){
            instance = new MyShooter();
        }
        return instance;
    }
    public void setPower1(double Speed){
        motor1.set(Speed);
    }
    public void setPower2(double Speed){
        motor2.set(TalonSRXControlMode.PercentOutput, Speed);
    }
    public void setPower3(double Speed){
        motor2.set(TalonSRXControlMode.PercentOutput, Speed);
    }


}
