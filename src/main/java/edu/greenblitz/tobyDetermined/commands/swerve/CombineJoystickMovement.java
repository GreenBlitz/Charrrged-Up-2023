package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.hid.SmartJoystick;

import java.util.function.DoubleSupplier;

public class CombineJoystickMovement extends SwerveCommand {
	static double ANG_SPEED_FACTOR = 5;
	static double LIN_SPEED_FACTOR = RobotMap.Swerve.MAX_VELOCITY;
	static double SLOW_ANG_SPEED_FACTOR = 0.25 * Math.PI;
	static double SLOW_LIN_SPEED_FACTOR = 0.5;
	private DoubleSupplier angSupplier;
	private boolean isSlow;

	
	public CombineJoystickMovement(boolean isSlow, DoubleSupplier angSupplier) {
		this.isSlow = isSlow;
		this.angSupplier = angSupplier;
	}
	
	public CombineJoystickMovement(boolean isSlow) {
		this(isSlow, () -> -OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_X));
	}
	
	@Override
	public void initialize() {
		ANG_SPEED_FACTOR = 5;
		LIN_SPEED_FACTOR = RobotMap.Swerve.MAX_VELOCITY;
		if (isSlow) {
			ANG_SPEED_FACTOR = SLOW_ANG_SPEED_FACTOR;
			LIN_SPEED_FACTOR = SLOW_LIN_SPEED_FACTOR;
		}
	}
	
	
	public void execute() {
		double leftwardSpeed = -OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.LEFT_X) * LIN_SPEED_FACTOR;
		double forwardSpeed = OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.LEFT_Y) * LIN_SPEED_FACTOR;
		double angSpeed = angSupplier.getAsDouble() * ANG_SPEED_FACTOR;
		angSpeed = Math.min(angSpeed, ANG_SPEED_FACTOR);
		if (forwardSpeed == 0 && leftwardSpeed == 0 && angSpeed == 0) {
			swerve.stop();
			return;
		}
		swerve.moveByChassisSpeeds(forwardSpeed, leftwardSpeed, angSpeed,
				swerve.getChassisAngle());
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		swerve.stop();
	}
}
