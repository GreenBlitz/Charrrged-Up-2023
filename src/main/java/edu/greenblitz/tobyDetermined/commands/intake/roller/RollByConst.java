package edu.greenblitz.tobyDetermined.commands.intake.roller;

public class RollByConst extends RollerCommand{
	private final double power;
	
	public RollByConst(double power) {
		this.power = power;
	}
	
	@Override
	public void execute() {
		roller.roll(power);
	}
	
	@Override
	public void end(boolean interrupted) {
		roller.stop();
	}
}
