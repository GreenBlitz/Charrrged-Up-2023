package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

public class ResetExtender extends ExtenderCommand {
	
	public static final double BACKWARDS_RESET_VELOCITY = -7;
	public static final double FORWARDS_RESET_VELOCITY = 3;
	
	@Override
	public void initialize() {
		super.initialize();
		extender.setResetFalse();
		double voltage = extender.getLimitSwitch() ? FORWARDS_RESET_VELOCITY : BACKWARDS_RESET_VELOCITY;
		extender.setMotorVoltage(Extender.getStaticFeedForward(Elbow.getInstance().getAngleRadians()) + voltage + Math.signum(voltage) * RobotMap.TelescopicArm.Elbow.kS);
	}
	
	@Override
	public boolean isFinished() {
		return super.isFinished() || extender.getLimitSwitch();
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		extender.resetLength();
		extender.stop();
	}
}
