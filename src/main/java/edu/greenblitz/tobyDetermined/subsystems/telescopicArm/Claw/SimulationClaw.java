package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.simulation.DoubleSolenoidSim;
import edu.wpi.first.wpilibj.simulation.SolenoidSim;

public class SimulationClaw implements IClaw{

	DoubleSolenoidSim solenoid;
	DCMotorSim motor;

	private double appliedVoltage;

	public SimulationClaw(){
		motor = new DCMotorSim(DCMotor.getNEO(1),1,0.001);
		solenoid = new DoubleSolenoidSim(
				PneumaticsModuleType.REVPH,
				0,
				1
		);
	}
	@Override
	public void setPower(double power) {
		setVoltage(12 * power);
	}

	@Override
	public void setVoltage(double voltage) {
		appliedVoltage = voltage;
		motor.setInputVoltage(voltage);
	}

	@Override
	public void setSolenoidState(DoubleSolenoid.Value value) {
		solenoid.set(value);
	}

	@Override
	public void updateInputs(ClawInputs inputs) {
		inputs.appliedOutput = appliedVoltage;
		inputs.rotationSpeed = motor.getAngularVelocityRPM();
		inputs.current = motor.getCurrentDrawAmps();
		inputs.isOpen =  solenoid.get() == DoubleSolenoid.Value.kForward; // cone mode
	}
}
