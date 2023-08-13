package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;

public class Shooterr extends GBSubsystem {
	private CANSparkMax motor ;
	private static Shooterr instance;
	
	private Shooterr(){
		motor = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
		motor.setSmartCurrentLimit(24);
		motor.setInverted(true);
	}
	
	public static Shooterr getInstance() {
		if (instance == null) {
			instance = new Shooterr();
		}
		return instance;
	}
	
	public void setPower(double power){
		motor.set(power);
	}
	
}