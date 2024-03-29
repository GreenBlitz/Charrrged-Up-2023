package edu.greenblitz.tobyDetermined.subsystems.intake;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class IntakeExtender extends GBSubsystem {
	private static IntakeExtender instance;
	private final DoubleSolenoid solenoid;

	private IntakeExtender() {
		solenoid = new DoubleSolenoid(RobotMap.Pneumatics.PneumaticsController.ID, RobotMap.Pneumatics.PneumaticsController.PCM_TYPE, RobotMap.Intake.Solenoid.FORWARD_PORT, RobotMap.Intake.Solenoid.REVERSE_PORT);
	}
	
	public static IntakeExtender getInstance() {
		init();
		return instance;
	}

	public static void init() {
		if(instance == null) {
			instance = new IntakeExtender();
		}
	}

	private void setValue(DoubleSolenoid.Value value) {
		solenoid.set(value);
	}

	public void extend() {
		setValue(DoubleSolenoid.Value.kForward);
	}

	public void retract() {
		setValue(DoubleSolenoid.Value.kReverse);
	}

	public boolean isExtended() {
		return solenoid.get().equals(DoubleSolenoid.Value.kForward);
	}

	public void toggleExtender() {
		if (isExtended()) {
			retract();
		} else {
			extend();
		}
	}

}
