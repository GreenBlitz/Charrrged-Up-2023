package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.RelativeEncoder;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;

public class DoSomthing extends GBSubsystem{
	private ColorSensorV3 cs;
	private GBSparkMax motor;
	
	private static DoSomthing instance;
	
	private DoSomthing() {
		motor = new GBSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
		motor.setSmartCurrentLimit(24);
		cs = new ColorSensorV3(I2C.Port.kOnboard);
		//motor.getEncoder().getPosition();
	}
	public void rotate3times(double n) {
		double num = n, error = num - motor.getEncoder().getPosition();
		double kp = 1/8.0;
		motor.set(kp*error);
	}
	public double getEncoderPosition() {
		return motor.getEncoder().getPosition();
	}
	public void setPowerByColor() {
		switch(getBallColor()) {
			case Red:
				motor.set(-0.8);
				break;
			case Blue:
				motor.set(0.8);
				break;
			case other:
				motor.set(0);
				break;
		}
	}
	public static DoSomthing getInstance() {
		
		if(instance == null) {
			instance = new DoSomthing();
		}
		return instance;
	}
	
	public void setPower(double power) {
		motor.set(power);
	}
	
	public colors getBallColor() {
		
		int r = cs.getRed();
		int g = cs.getGreen();
		int b = cs.getBlue();
		
		if(r > g && r > b) {
			return colors.Red;
		}
		else if(b > r && b > g) {
			return colors.Blue;
		}
		else {
			return colors.other;
		}
		
	}
	public enum colors {
		Red,
		Blue,
		other,
	}
}
