package edu.greenblitz.tobyDetermined.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.Timer;


public class MyShooter extends GBSubsystem {
	private CANSparkMax upperMotor;
	private TalonSRX middleMotor;
	private TalonSRX lowerMotor;
	private static MyShooter instance;
	public final double speed = 1;
	public boolean redIn;
	public boolean BlueDetected;
	public Timer timer;

	private MyShooter() {
		upperMotor = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
		middleMotor = new TalonSRX(5);
		lowerMotor = new TalonSRX(6);
		timer = new Timer();
	}

	public static MyShooter getInstance() {
		if (instance == null) {
			instance = new MyShooter();
		}
		return instance;
	}

	public void setUpperPower(double Speed) {
		upperMotor.set(Speed * speed);
	}

	public void setMiddlePower(double Speed) {
		middleMotor.set(TalonSRXControlMode.PercentOutput, Speed * speed);
	}

	public void setLowerPower(double Speed) {
		lowerMotor.set(TalonSRXControlMode.PercentOutput, Speed * speed);
	}

	public double getMiddlePower(){
		return middleMotor.getMotorOutputPercent();
	}
}