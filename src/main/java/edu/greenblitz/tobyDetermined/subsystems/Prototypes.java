package edu.greenblitz.tobyDetermined.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.utils.motors.GBSparkMax;

import java.util.HashMap;

public class Prototypes {
	public static final HashMap<Integer, GBSparkMax> sparks = new HashMap<>();
	public static final HashMap<Integer, TalonSRX> talons = new HashMap<>();

	public static GBSparkMax getSparkMax(int id){
		if (!sparks.containsKey(id)){
			sparks.put(id, new GBSparkMax(id, CANSparkMaxLowLevel.MotorType.kBrushless));
		}
		return sparks.get(id);
	}

	public static TalonSRX getTalon(int id){
		if (!talons.containsKey(id)){
			talons.put(id, new TalonSRX(id));
		}
		return talons.get(id);
	}
}
