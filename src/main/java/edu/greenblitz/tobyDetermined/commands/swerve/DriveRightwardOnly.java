package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.AnalogInput;

public class DriveRightwardOnly extends SwerveCommand {
	private final AnalogInput rightUltrasonicSensor = new AnalogInput(0);
	private final AnalogInput leftUltrasonicSensor = new AnalogInput(1);
	private static final double SLOW_ANG_SPEED_FACTOR = Math.PI;
	private static final double SLOW_LIN_SPEED_FACTOR = 0.5;
	private static final int minSensorValueToStop = 250; //todo change to real;
	
	@Override
	public void execute() {
		//needs to add an if to make sure that the chassis won't fall of the ramp
		;
		if (rightUltrasonicSensor.getValue() > minSensorValueToStop && leftUltrasonicSensor.getValue() > minSensorValueToStop) {
			swerve.moveByChassisSpeeds(
					0,
					OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.LEFT_X) * SLOW_LIN_SPEED_FACTOR,
					OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_X) * SLOW_ANG_SPEED_FACTOR,
					swerve.getChassisAngle());
		}
	}
	
	@Override
	public void end(boolean interrupted) {
		swerve.stop();
	}
}
