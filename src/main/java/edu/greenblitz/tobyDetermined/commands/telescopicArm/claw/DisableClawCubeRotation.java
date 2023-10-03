package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

public class DisableClawCubeRotation extends ClawCommand{


	public DisableClawCubeRotation(){
	}

	@Override
	public void initialize() {
		claw.motorGrip(0);
	}

	@Override
	public void end(boolean interrupted) {
		claw.motorGrip(0);
	}
}
