package edu.greenblitz.tobyDetermined.subsystems.intake;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeExtender extends GBSubsystem {
	private static IntakeExtender instance;
	private final DoubleSolenoid solenoid;

	private IntakeExtender() {
		solenoid = new DoubleSolenoid(RobotMap.Pneumatics.PCM.PCM_ID, RobotMap.Pneumatics.PCM.PCM_TYPE, RobotMap.Intake.Solenoid.FORWARD_PORT, RobotMap.Intake.Solenoid.REVERSE_PORT);
	}
	
	public static IntakeExtender getInstance() {
		if(instance == null) {
			init();
			SmartDashboard.putBoolean("intake extender initialized via getinstance", true);
		}
		return instance;
	}

	public static void init(){
		instance = new IntakeExtender();
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
