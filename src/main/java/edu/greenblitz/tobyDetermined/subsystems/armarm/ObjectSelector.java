package edu.greenblitz.tobyDetermined.subsystems.armarm;

import edu.greenblitz.tobyDetermined.subsystems.LED;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.wpi.first.wpilibj.util.Color;

public class ObjectSelector {
	private static boolean isCone = true;
	
	public static void selectCone(){
		isCone = true;
		LED.getInstance().setColor(Color.kYellow);
		Claw.getInstance().coneCatchMode();
	}
	
	public static void selectCube(){
		isCone = false;
		LED.getInstance().setColor(Color.kMagenta);
		Claw.getInstance().cubeCatchMode();
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
