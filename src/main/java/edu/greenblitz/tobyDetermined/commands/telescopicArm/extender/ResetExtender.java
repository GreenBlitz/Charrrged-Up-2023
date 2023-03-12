package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

public class ResetExtender extends ExtenderCommand {
	
	public static final double BACKWARDS_RESET_VELOCITY = -7;
	private double voltage;
	
	public ResetExtender(double voltage){
		this.voltage = voltage;
	}
	
	public ResetExtender(){
		this(BACKWARDS_RESET_VELOCITY);
	}
	
	
	@Override
	public void initialize() {
		super.initialize();
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
		extender.enableReverseLimit();
		extender.stop();
	}
}
