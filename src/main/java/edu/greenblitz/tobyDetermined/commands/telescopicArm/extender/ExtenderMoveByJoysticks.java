package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.utils.hid.SmartJoystick;

public class ExtenderMoveByJoysticks extends ExtenderCommand {
	private SmartJoystick joystick;
	public ExtenderMoveByJoysticks(SmartJoystick joystick) {
		super();
		this.joystick = joystick;
	}

	@Override
	public void initialize() {
		super.initialize();
	}

	@Override
	public void execute() {
		double power = -joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y) * 0.4;
		extender.debugSetPower(power);
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		extender.stop();
	}
}
