package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

public class ResetExtender extends ExtenderCommand {
	
	public static final double BACKWARDS_RESET_VELOCITY = -0.2;
	public static final double FORWARDS_RESET_VELOCITY = 0.05;
	
	@Override
	public void initialize() {
		super.initialize();
		double velocity = extender.getLimitSwitch() ? FORWARDS_RESET_VELOCITY : BACKWARDS_RESET_VELOCITY;
		extender.setMotorVoltage(Extender.getFeedForward(velocity, 0, Elbow.getInstance().getAngleRadians()));
	}
	
	@Override
	public boolean isFinished() {
		return super.isFinished() || extender.didSwitchFlip();
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		extender.resetLength();
		extender.stop();
	}
}
