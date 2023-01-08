package edu.greenblitz.pegasus.commands.funnel;

public class RunFunnel extends FunnelCommand {
	@Override
	public void execute() {
		funnel.moveMotor();
	}

	@Override
	public void end(boolean interrupted) {
		funnel.stopMotor();
	}

	@Override
	public boolean isFinished() {
		return false;
	}

}
