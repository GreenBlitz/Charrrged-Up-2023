//package edu.greenblitz.tobyDetermined.commands.swerve;
//
//import edu.greenblitz.tobyDetermined.RobotMap;
//import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassis;
//
//public class DriveSidewaysUntilEdge extends SwerveCommand {
//	/**
//	 * drive rightwards until the ultrasonic sensor finds no floor
//	 */
//	private static final double SLOW_LIN_SPEED_FACTOR = 0.4;
//	private double speed;
//
//	public enum Direction{
//		RIGHT,
//		LEFT
//	}
//
//	public DriveSidewaysUntilEdge(Direction direction, double speed) {
//		switch (direction){
//			case LEFT:
//				this.speed = speed * -1;
//				break;
//			case RIGHT:
//				this.speed = speed;
//				break;
//		}
//	}
//
//	@Override
//	public void execute() {
//		if (SwerveChassis.getInstance().getUltrasonicDistance() < RobotMap.Ultrasonic.DISTANCE_FROM_FLOOR_TO_STOP_IN_MM) {
//			swerve.moveByChassisSpeeds(
//					0,
//					speed * SLOW_LIN_SPEED_FACTOR,
//					0,
//					swerve.getChassisAngle());
//		} else {
//			swerve.stop();
//		}
//	}
//
//	@Override
//	public void end(boolean interrupted) {
//		swerve.stop();
//	}
//}
