package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

import edu.greenblitz.tobyDetermined.subsystems.LED;
import edu.wpi.first.wpilibj.util.Color;

public class ObjectSelector {
	private static boolean isCone = true;
	
	public static void selectCone(){
		isCone = true;
		LED.getInstance().setColor(Color.kYellow);
	}
	
	public static void selectCube(){
		isCone = false;
		LED.getInstance().setColor(Color.kMagenta);
	}
	
	public static boolean IsCone(){
		return isCone;
	}
	
	public static boolean IsCube(){
		return !isCone;
	}
	
	public static void flipSelection(){
		if (isCone){
			selectCube();
		} else {
			selectCone();
		}
	}
	
}
