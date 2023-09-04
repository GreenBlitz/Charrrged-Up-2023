package edu.greenblitz.tobyDetermined.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

<<<<<<< HEAD
public class MyShooter extends GBSubsystem {
	private CANSparkMax upperMotor;
	private TalonSRX middleMotor;
	private TalonSRX lowerMottor;
	private static MyShooter instance;
	public int Vommit = 1;

	private MyShooter() {
		upperMotor = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
		middleMotor = new TalonSRX(5);
		lowerMottor = new TalonSRX(6);
	}

	public static MyShooter getInstace() {
		if (instance == null) {
			instance = new MyShooter();
		}
		return instance;
	}

	public void setUpperPower(double Speed) {
		upperMotor.set(Speed * Vommit);
	}

	public void setMiddlePower(double Speed) {
		middleMotor.set(TalonSRXControlMode.PercentOutput, Speed * Vommit);
	}

	public void setLowerPower(double Speed) {
		lowerMottor.set(TalonSRXControlMode.PercentOutput, Speed * Vommit);
	}
=======
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
>>>>>>> d6fcbf096acbc1268fe36488e6632e492414dda3


}
