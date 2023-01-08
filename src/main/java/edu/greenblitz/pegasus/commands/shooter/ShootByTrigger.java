package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.pegasus.utils.hid.SmartJoystick;

public class ShootByTrigger extends ShooterByRPM {//todo remake later when its relevant

	private static final double DEADZONE = 0.1;
	private final SmartJoystick joystick;
	private final SmartJoystick.Axis axis;


	public ShootByTrigger(double target, SmartJoystick joystick, SmartJoystick.Axis axis) {
		super(target);
		this.joystick = joystick;
		this.axis = axis;
	}

	@Override
	public void execute() {
		if (joystick.getAxisValue(axis) > DEADZONE) {
			super.execute();
		}
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		shooter.setSpeedByPID(0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
