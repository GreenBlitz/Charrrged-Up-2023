package edu.greenblitz.tobyDetermined.commands.telescopicArm.extender;

import edu.greenblitz.tobyDetermined.subsystems.Battery;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ExtenderMoveByJoysticks extends ExtenderCommand {
	private SmartJoystick joystick;
	public ExtenderMoveByJoysticks(SmartJoystick joystick) {
		super();
		this.joystick = joystick;
	}

	@Override
	public void execute() {
		double power = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
		double ff = Extender.getStaticFeedForward(Elbow.getInstance().getAngleRadians());
		SmartDashboard.putNumber("joystick + ff volt",power * Battery.getInstance().getCurrentVoltage() + ff);

		SmartDashboard.putNumber("ext velocity", extender.getVelocity());

		
		extender.setPower((power * Battery.getInstance().getCurrentVoltage() + ff) / Battery.getInstance().getCurrentVoltage());
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		extender.stop();
	}
}
