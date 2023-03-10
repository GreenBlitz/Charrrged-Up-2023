package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		double power = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y) * 1;
		double ff = Extender.getStaticFeedForward(Elbow.getInstance().getAngleRadians());
		SmartDashboard.putNumber("volt",power * Battery.getInstance().getCurrentVoltage() + ff);
		extender.setMotorVoltage(power * Battery.getInstance().getCurrentVoltage() + ff);
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		extender.stop();
	}
}
