package edu.greenblitz.tobyDetermined.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.I2C;

import java.awt.*;

public class IntakeObjSensor extends GBSubsystem {
	/**
	 * it senses the object inside (cone/cube) using the color sensor,
	 * and displays it on the dashboard
	 */

	public enum Obj {
		CONE,
		CUBE,
		NONE,
	}
	private ColorSensorV3 cs = RobotMap.cs;
	public static IntakeObjSensor instance;

	public static IntakeObjSensor getInstance() {
		return instance;
	}

	public Obj getObject(){
		float[] color = Color.RGBtoHSB(cs.getRed(), cs.getGreen(), cs.getBlue(), new float[3]);
		if(color[0] >= 0.22 && color[0] <= 2.9){
			return Obj.CONE;
		}
		else if (color[0] >= 0.53 && color[0] <= 0.59){
			return Obj.CUBE;
		}
		else return Obj.NONE;
	}
}
