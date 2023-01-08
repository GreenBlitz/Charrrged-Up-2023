package edu.greenblitz.pegasus.commands.shooter;

import org.greenblitz.debug.RemoteCSVTarget;

public class ShootByConstant extends ShooterCommand {

	protected double power;
	protected RemoteCSVTarget logger;
	protected long tStart;

	public ShootByConstant(double power) {
		this.power = power;
	}

	@Override
	public void initialize() {
		super.initialize();
		tStart = System.currentTimeMillis();
	}

	@Override
	public void execute() {
		shooter.setPower(power);

	}

	@Override
	public void end(boolean interrupted) {
		shooter.setPower(0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
