package edu.greenblitz.tobyDetermined.commands.rotatingBelly;

public class RotateByPower extends RotatingBellyCommand {
	private double power;

	public RotateByPower(double power) {
		this.power = power;
	}

	@Override
	public void execute() {
		belly.setPower(power);
	}

	@Override
	public void end(boolean interrupted) {
		belly.stop();
	}
}
