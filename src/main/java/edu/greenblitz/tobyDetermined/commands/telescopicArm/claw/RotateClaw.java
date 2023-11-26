package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

public class RotateClaw extends ClawCommand{

	@Override
	public void initialize() {
		claw.motorGrip();
	}

	@Override
	public void end(boolean interrupted) {
		claw.stopMotor();
	}
}
