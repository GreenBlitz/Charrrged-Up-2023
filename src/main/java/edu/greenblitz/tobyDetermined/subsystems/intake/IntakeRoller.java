package edu.greenblitz.tobyDetermined.subsystems.intake;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;

public class IntakeRoller extends GBSubsystem {
	private static IntakeRoller instance;
	private final GBSparkMax motor;
	private DigitalInput objectDetector;

	private IntakeRoller() {
		motor = new GBSparkMax(RobotMap.Intake.ROLLER_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
		motor.config(RobotMap.Intake.INTAKE_CONFIG_OBJECT);
		objectDetector = new DigitalInput(RobotMap.Intake.BEAM_BREAKER_ID);
	}

	public static IntakeRoller getInstance() {
		init();
		return instance;
	}

	public static void init(){
		if(instance == null) {
			instance = new IntakeRoller();
		}
	}

	public void roll(double power) {
		motor.set(power);
	}

	public void rollIn() {
		roll(RobotMap.Intake.DEFAULT_POWER);
	}

	public void rollOut() {
		roll(-RobotMap.Intake.DEFAULT_POWER);
	}

	public void stop() {
		roll(0);
	}

	public boolean isObjectIn(){
		return objectDetector.get();
	}
}
