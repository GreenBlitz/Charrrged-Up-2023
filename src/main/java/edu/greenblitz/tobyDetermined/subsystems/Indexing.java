package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static edu.wpi.first.wpilibj.DriverStation.Alliance.*;

public class Indexing extends GBSubsystem {
	private ColorSensorV3 Scolor;
	private DigitalInput macroSwitch;
	private static Indexing instance;
	private final int minBlue = 250;
	private final int minRed = 200;

	public boolean isEnemyBallIn() {
		if (DriverStation.getAlliance() != colorCheck() && colorCheck() != Invalid) {
			return true;
		}
		return false;
	}

	private Indexing(){
		macroSwitch = new DigitalInput(8);
		Scolor = new ColorSensorV3(I2C.Port.kOnboard);
	}
	public boolean ballIn() {
		return colorCheck() != Invalid;
	}

	public static Indexing getInstance() {
		if (instance == null)
			instance = new Indexing();
		return instance;
	}

	public boolean switchOn() {
		return macroSwitch.get();
	}
	public int getRed() {
		return Scolor.getRawColor().red;
	}
	public int getGreen() {
		return Scolor.getRawColor().green;
	}
	public int getBlue() {
		return Scolor.getRawColor().blue;
	}

	public DriverStation.Alliance colorCheck() {






//		SmartDashboard.putNumber("blue",Scolor.getRawColor().blue);
//		SmartDashboard.putNumber("red",Scolor.getRawColor().red);
//		SmartDashboard.putNumber("green",Scolor.getRawColor().green);


		if (Scolor.getRawColor().blue > minBlue)
			return Blue;
		else if (Scolor.getRawColor().red > minRed)
			return Red;
		else
			return Invalid;
	}

}
