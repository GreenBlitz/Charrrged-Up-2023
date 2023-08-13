package edu.greenblitz.tobyDetermined.subsystems.intake;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeRoller extends GBSubsystem {
	private static IntakeRoller instance;
	private final TalonSRX motor;
	private DigitalInput objectDetector;

	private IntakeRoller() {
		motor = new TalonSRX(RobotMap.Intake.ROLLER_ID); //todo find real id
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
		motor.set(TalonSRXControlMode.PercentOutput,power);
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
