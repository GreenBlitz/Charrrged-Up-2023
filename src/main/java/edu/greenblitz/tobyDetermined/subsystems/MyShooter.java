package edu.greenblitz.tobyDetermined.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.commands.StopShooter;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class MyShooter extends GBSubsystem {
	private CANSparkMax upperMotor;
	private TalonSRX middleMotor;
	private TalonSRX lowerMotor;
	private static MyShooter instance;
	private static final double speed = 0.4;
	public boolean redIn;

	private MyShooter() {
		upperMotor = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
		middleMotor = new TalonSRX(5);
		lowerMotor = new TalonSRX(6);
	}

	public static MyShooter getInstace() {
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

	public void activateShooterBlue(){
		MyShooter.getInstace().setMiddlePower(-0.5 * speed);
		MyShooter.getInstace().setUpperPower(-0.5 * speed);
		new WaitCommand(2).schedule();
		new StopShooter().schedule();
	}
	public void activateShooterRed(){
		MyShooter.getInstace().setMiddlePower(-0.5 * speed);
		MyShooter.getInstace().setUpperPower(-0.5 * speed);
		redIn = false;
	}
}
