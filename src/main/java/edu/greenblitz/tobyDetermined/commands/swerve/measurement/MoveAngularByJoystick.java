package edu.greenblitz.tobyDetermined.commands.swerve.measurement;

import edu.greenblitz.tobyDetermined.OI;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveAngularByJoystick extends SwerveCommand {
	
	private double prevSpeed;
	private long prevTimeMilli;
	
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
		if (angularSpeed == 0) {
			swerve.stop();
			return;
		}
		swerve.rotateChassisByPower(angularSpeed);
		if (timer.advanceIfElapsed(0.2)) {
			double curSpeed = swerve.getChassisSpeeds().omegaRadiansPerSecond;
			long curTimeMilli = System.currentTimeMillis();
			double maxAcl = (curSpeed - prevSpeed) / ((curTimeMilli - prevTimeMilli) / 1000.0);
			prevTimeMilli = curTimeMilli;
			prevSpeed = curSpeed;
			maxAcl = Math.max(Math.abs(maxAcl), SmartDashboard.getNumber("max acceleration", 0));
			SmartDashboard.putNumber("max acceleration", maxAcl);
			
		}
		double maxSpeed = Math.max(Math.abs(swerve.getChassisSpeeds().omegaRadiansPerSecond), SmartDashboard.getNumber("max ang speed", 0));
		
		SmartDashboard.putNumber("max ang speed", maxSpeed);
		SmartDashboard.putNumber("max lin speed", maxSpeed * RobotMap.Swerve.SwerveLocationsInSwerveKinematicsCoordinates[0].getNorm());
	}
	
}
