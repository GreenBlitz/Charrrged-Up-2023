package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;

import java.awt.*;


public class ThingsToHandleBalls extends GBSubsystem {
	private static ThingsToHandleBalls instance;
	private ColorSensorV3 cs;
	public DriverStation.Alliance alliance;
	
	private DigitalInput switchCensor;
	
	private ThingsToHandleBalls() {
		cs = new ColorSensorV3(I2C.Port.kOnboard);
		alliance = DriverStation.getAlliance();
		switchCensor = new DigitalInput(0);
	}
	
	public static ThingsToHandleBalls getInstance() {
		if (instance == null) {
			instance = new ThingsToHandleBalls();
		}
		return instance;
	}
	
	public boolean getSwitch(){
		return switchCensor.get();
	}
	
	public DriverStation.Alliance getColor(){
		float[] colors = Color.RGBtoHSB(cs.getRed(),cs.getGreen(),cs.getBlue(),new float[3]);
		if(colors[0]>=0.05 && colors[0]<0.2)
			return DriverStation.Alliance.Red;
		else if (colors[0]>=0.4 && colors[0]<0.6)
			return DriverStation.Alliance.Blue;
		return DriverStation.Alliance.Invalid;
	}
	
	public enum BallColor{
		TEAMALLIANCE,
		ENEMYALLIANCE,
		NOTHING
		
	}
	
	public BallColor IsBallGood(){
		DriverStation.Alliance a = instance.getColor();
		if (a==alliance)
			return BallColor.TEAMALLIANCE;
		else if( a!= DriverStation.Alliance.Invalid)
			return BallColor.ENEMYALLIANCE;
		return BallColor.NOTHING;
	}
	
	
}