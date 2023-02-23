package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.ElbowCommand;
import edu.greenblitz.utils.hid.SmartJoystick;

public class moveByJoysticks extends ExtenderCommand {
	public moveByJoysticks() {
		super();
	}
	@Override
	public void initialize() {
		super.initialize();
	}

	@Override
	public void execute() {
		double power = -OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_Y) * 0.5;
		extender.debugSetPower(power);
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		extender.stop();
	}
}
