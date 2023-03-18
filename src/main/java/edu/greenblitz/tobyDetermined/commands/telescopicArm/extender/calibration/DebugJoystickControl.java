package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.calibration;

import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtenderCommand;
import edu.greenblitz.utils.hid.SmartJoystick;

public class DebugJoystickControl extends ExtenderCommand {
	
	private SmartJoystick joystick;
	public DebugJoystickControl(SmartJoystick joystick) {
		super();
		this.joystick = joystick;
	}
	
	@Override
	public void initialize() {
		super.initialize();
		extender.disableAllLimits();
	}
	
	@Override
	public void execute() {
		double power = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y) * 2;
		
		extender.debugSetPower(power);
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		extender.stop();
	}
}
