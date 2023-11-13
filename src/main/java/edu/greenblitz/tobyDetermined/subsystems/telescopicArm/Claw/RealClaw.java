package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.motors.GBSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import static edu.greenblitz.tobyDetermined.RobotMap.Pneumatics.PneumaticsController.ID;

public class RealClaw implements IClaw{

	private GBSparkMax motor;
	private DoubleSolenoid solenoid;

	public RealClaw(){
		motor = new GBSparkMax(RobotMap.TelescopicArm.Claw.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
		motor.config(RobotMap.TelescopicArm.Claw.CLAW_CONFIG_OBJECT);
		solenoid = new DoubleSolenoid(ID, RobotMap.Pneumatics.PneumaticsController.PCM_TYPE, RobotMap.TelescopicArm.Claw.SOLENOID_OPEN_CLAW_ID, RobotMap.TelescopicArm.Claw.SOLENOID_CLOSED_CLAW_ID);
	}

	@Override
	public void setPower(double power) {
		motor.set(power);
	}

	@Override
	public void setVoltage(double voltage) {
		motor.setVoltage(voltage);
	}

	@Override
	public void setSolenoidState(DoubleSolenoid.Value value) {
		solenoid.set(value);
	}

	@Override
	public void updateInputs(ClawInputs inputs) {
		inputs.appliedOutput = motor.getAppliedOutput();
		inputs.rotationSpeed = motor.getEncoder().getVelocity();
		inputs.current = motor.getOutputCurrent();
		inputs.isOpen =  solenoid.get() == DoubleSolenoid.Value.kForward; // cone mode
	}
}
