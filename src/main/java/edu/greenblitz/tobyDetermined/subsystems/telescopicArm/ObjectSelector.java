package edu.greenblitz.tobyDetermined.subsystems.telescopicArm;

public class ObjectSelector {
	private static boolean isCone = true;
	
	public static void selectCone(){
		isCone = true;
	}
	
	public static void selectCube(){
		isCone = false;
	}
	
	public static boolean IsCone(){
		return isCone;
	}
	
	public static boolean IsCube(){
		return !isCone;
	}
	
	public static void flipSelection(){
		isCone = !isCone;
	}
	
}
