package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

public class Grip extends ClawCommand{

	@Override
	public void execute() {
		claw.motorGrip();
	}

	@Override
	public void end(boolean interrupted) {
		claw.stopMotor();
	}
}
