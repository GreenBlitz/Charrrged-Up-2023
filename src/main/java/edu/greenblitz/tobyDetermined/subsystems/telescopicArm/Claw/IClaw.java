package edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.ExtenderInputs;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public interface IClaw {

	void setPower(double power);
	void setVoltage(double voltage);
	void setSolenoidState(DoubleSolenoid.Value value);
	void updateInputs(ClawInputs inputs);

}
