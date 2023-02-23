package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.utils.hid.SmartJoystick;

public class elbowMoveByJoysticks extends ElbowCommand {
	private SmartJoystick joystick;
	public elbowMoveByJoysticks(SmartJoystick joystick) {
		super();
		this.joystick = joystick;
	}
	@Override
	public void initialize() {
		super.initialize();
	}

	@Override
	public void execute() {
		double power = -joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y) * 0.5;
		elbow.debugSetPower(power);
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		elbow.stop();
	}
}
