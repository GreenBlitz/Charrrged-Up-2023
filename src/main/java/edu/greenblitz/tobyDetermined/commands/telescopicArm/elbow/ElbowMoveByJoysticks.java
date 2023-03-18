package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElbowMoveByJoysticks extends ElbowCommand {
	private SmartJoystick joystick;
	public ElbowMoveByJoysticks(SmartJoystick joystick) {
		super();
		this.joystick = joystick;
	}
	@Override
	public void initialize() {
		super.initialize();
	}

	@Override
	public void execute() {
		double power = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y) * 0.75;
		SmartDashboard.putNumber("volt elbow", power * Battery.getInstance().getCurrentVoltage() + Elbow.getStaticFeedForward(Extender.getInstance().getLength(), elbow.getAngleRadians()));
		SmartDashboard.putNumber("elbow vel", elbow.getVelocity());
		elbow.setMotorVoltage(power * Battery.getInstance().getCurrentVoltage() + Elbow.getStaticFeedForward(Extender.getInstance().getLength(), elbow.getAngleRadians()));
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		elbow.stop();
	}
}
