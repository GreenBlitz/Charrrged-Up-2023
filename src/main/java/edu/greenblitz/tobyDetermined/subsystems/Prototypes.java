package edu.greenblitz.tobyDetermined.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.greenblitz.utils.motors.GBSparkMax;

import java.util.HashMap;

public class Prototypes {
	public static final HashMap<Integer, GBSparkMax> sparks = new HashMap<>();
	public static final HashMap<Integer, TalonSRX> talons = new HashMap<>();
}
