package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.pegasus.OI;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.utils.hid.SmartJoystick;

import java.util.function.DoubleSupplier;

public class CombineJoystickMovement extends SwerveCommand {
	private DoubleSupplier angSupplier;
	
	
	static double ANG_SPEED_FACTOR = RobotMap.Pegasus.Swerve.MAX_ANGULAR_SPEED;
	static double LIN_SPEED_FACTOR = RobotMap.Pegasus.Swerve.MAX_VELOCITY;
	
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
		if (isSlow) {
			ANG_SPEED_FACTOR = RobotMap.Pegasus.Swerve.MAX_ANGULAR_SPEED * 0.01;
			LIN_SPEED_FACTOR = RobotMap.Pegasus.Swerve.MAX_VELOCITY * 0.025;
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
