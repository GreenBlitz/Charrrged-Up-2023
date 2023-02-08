package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;

public class DriveRightwardOnly extends SwerveCommand {
	private static final double SLOW_LIN_SPEED_FACTOR = 0.4;
	private double speed;


	public DriveRightwardOnly(boolean right, double speed){
		this.speed = speed;
		if(right) this.speed *= -1;
	}

	@Override
	public void execute() {
		if (SwerveChassis.getInstance().getUltrasonicDistance() < RobotMap.Ultrasonic.DISTANCE_FROM_FLOOR_TO_STOP_IN_MM) {
			swerve.moveByChassisSpeeds(
					0,
					speed * SLOW_LIN_SPEED_FACTOR,
					0,
					swerve.getChassisAngle());
		}
		else {
			swerve.stop();
		}
	}

	@Override
	public void end(boolean interrupted) {
		swerve.stop();
	}
}
