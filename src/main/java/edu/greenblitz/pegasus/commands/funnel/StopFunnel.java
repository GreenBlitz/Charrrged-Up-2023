package edu.greenblitz.pegasus.commands.funnel;

public class StopFunnel extends FunnelCommand {
	@Override
	public void initialize() {
		funnel.stopMotor();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
