package edu.greenblitz.tobyDetermined.commands.swerve.measurement;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CalibrateMaxSpeedAngular extends SwerveCommand {
	
	private double prevSpeed;
	private long prevTimeMilli;
	private static final double TIME_TO_ACCELERATE = 0.2;
	
	private Timer timer;
	
	@Override
	public void initialize() {
		timer = new Timer();
		timer.start();
		super.initialize();
		SmartDashboard.putNumber("max ang speed", 0);
	}
	
	public void execute() {
		double angularSpeed = -OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_X);
		swerve.rotateChassisByPower(angularSpeed);
		if (timer.advanceIfElapsed(TIME_TO_ACCELERATE)) {
			double curSpeed = swerve.getChassisSpeeds().omegaRadiansPerSecond;
			long curTimeMilli = System.currentTimeMillis();
			double maxAcl = (curSpeed - prevSpeed) / Units.millisecondsToSeconds(curTimeMilli - prevTimeMilli);
			prevTimeMilli = curTimeMilli;
			prevSpeed = curSpeed;
			maxAcl = Math.max(Math.abs(maxAcl), SmartDashboard.getNumber("max angular acceleration", 0));
			SmartDashboard.putNumber("max angular acceleration", maxAcl);
			SmartDashboard.putNumber("max lin acceleration", maxAcl * RobotMap.Swerve.SwerveLocationsInSwerveKinematicsCoordinates[0].getNorm());
		}
		double maxSpeed = Math.max(Math.abs(swerve.getChassisSpeeds().omegaRadiansPerSecond), SmartDashboard.getNumber("max ang speed", 0));
		
		SmartDashboard.putNumber("max ang speed", maxSpeed);
		SmartDashboard.putNumber("max lin speed", maxSpeed * RobotMap.Swerve.SwerveLocationsInSwerveKinematicsCoordinates[0].getNorm());
	}

}
