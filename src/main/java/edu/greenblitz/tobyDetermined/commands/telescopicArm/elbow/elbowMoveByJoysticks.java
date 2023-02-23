package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.utils.hid.SmartJoystick;

public class elbowMoveByJoysticks extends ElbowCommand {
	public elbowMoveByJoysticks() {
		super();
	}
	@Override
	public void initialize() {
		super.initialize();
	}

	@Override
	public void execute() {
		double power = -OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.LEFT_Y) * 0.5;
		elbow.debugSetPower(power);
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		elbow.stop();
	}
}
