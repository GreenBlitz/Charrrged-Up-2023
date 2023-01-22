//package edu.greenblitz.tobyDetermined.commands.swerve;
//
//import edu.greenblitz.tobyDetermined.OI;
//import edu.greenblitz.utils.hid.SmartJoystick;
//import edu.wpi.first.wpilibj.AnalogInput;
//import edu.wpi.first.wpilibj.DigitalInput;
//
//public class DriveRightwardOnly extends SwerveCommand {
//	private final AnalogInput rightUltrasonicSensor = new AnalogInput(0);
//	private final AnalogInput leftUltrasonicSensor = new AnalogInput(1);
//	private static final double SLOW_ANG_SPEED_FACTOR = Math.PI;
//	private static final double SLOW_LIN_SPEED_FACTOR = 0.5;
//	private static final int minSensorValueToStop = 250; //todo change to real;
//	private double speed;
//
//
//	public DriveRightwardOnly(boolean left, double speed){
//		this.speed = speed;
//		if(left) this.speed *= -1;
//	}
//
//	@Override
//	public void execute() {
//		//needs to add an if to make sure that the chassis won't fall of the ramp
//		;
//		if (rightUltrasonicSensor.getValue() > minSensorValueToStop && leftUltrasonicSensor.getValue() > minSensorValueToStop) {
//			swerve.moveByChassisSpeeds(
//					0,
//					speed * SLOW_LIN_SPEED_FACTOR,
////					OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_X) * SLOW_ANG_SPEED_FACTOR,
//					0,
//					swerve.getChassisAngle());
//		}
//	}
//
//	@Override
//	public void end(boolean interrupted) {
//		swerve.stop();
//	}
//}
