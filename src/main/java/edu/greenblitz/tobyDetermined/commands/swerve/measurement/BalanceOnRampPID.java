package edu.greenblitz.tobyDetermined.commands.swerve.measurement;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.utils.PigeonGyro;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BalanceOnRampPID extends SwerveCommand {
	
	private PIDController balanceController;
	private PigeonGyro gyro;
	private double currentAngle =0;
	private double lastAngle = 0;
	private boolean hasPassedHighPoint;
	private final double highPoint = Math.toRadians(16);
	private double minAngleChangeToStop = Math.toRadians(0.25);
	private final double speed = 0.25;
	
	public BalanceOnRampPID(){
//		this.balanceController = RobotMap.Swerve.balancePID.getPIDController();
		this.gyro = swerve.getPigeonGyro();
	}
	
	@Override
	public void execute() {
		lastAngle = currentAngle;
		currentAngle = Math.abs(gyro.getRoll());
//		swerve.moveByChassisSpeeds(
//				balanceController.calculate(gyro.getRoll()),
//				0,0,0
//				);
		swerve.moveByChassisSpeeds(speed * -Math.signum(gyro.getRoll()), 0,0,0);
		
		if (currentAngle > highPoint){hasPassedHighPoint = true;}
		SmartDashboard.putBoolean("hasPassedHighPoint" ,hasPassedHighPoint);
		SmartDashboard.putBoolean("on", true);
		SmartDashboard.putNumber("delta", currentAngle - lastAngle);
	}
	
	@Override
	public boolean isFinished() {
		if(currentAngle - lastAngle <= -1 * minAngleChangeToStop && hasPassedHighPoint){
			SmartDashboard.putBoolean("on",false);
			return true;
		}
		lastAngle = Math.abs(gyro.getRoll());
		return false;
	}
	
	@Override
	public void end(boolean interrupted) {
		swerve.stop();
		
		hasPassedHighPoint = false;
		SmartDashboard.putBoolean("hasPassedHighPoint" ,hasPassedHighPoint);
	}
}
