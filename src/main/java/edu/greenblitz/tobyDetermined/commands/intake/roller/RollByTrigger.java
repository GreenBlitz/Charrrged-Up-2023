package edu.greenblitz.tobyDetermined.commands.intake.roller;

import edu.greenblitz.utils.hid.SmartJoystick;

public class RollByTrigger extends RollerCommand {
	private final SmartJoystick joystick;

	public RollByTrigger(SmartJoystick joystick) {
		this.joystick = joystick;
	}

	@Override
	public void execute() {
		double power = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_TRIGGER);
		intake.moveRoller(-power);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		intake.moveRoller(0);
	}
}
