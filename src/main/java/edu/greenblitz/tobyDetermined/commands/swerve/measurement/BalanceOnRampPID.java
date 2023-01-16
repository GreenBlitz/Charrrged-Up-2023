package edu.greenblitz.tobyDetermined.commands.swerve.measurement;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.utils.PigeonGyro;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BalanceOnRampPID extends SwerveCommand {
	
	PIDController balanceController;
	PigeonGyro gyro;
	double currentAngle =0;
	double lastAngle = 0;
	double[] accelerationArray = new double[3];
	double minAngleChangeToStop = Math.toRadians(0.25); //lastly was .3
	public BalanceOnRampPID(){
		this.balanceController = RobotMap.Swerve.balancePID.getPIDController();
		this.gyro = swerve.getPigeonGyro();
	}
	
	@Override
	public void execute() {
		lastAngle = currentAngle;
		currentAngle = Math.abs(gyro.getRoll());
		swerve.moveByChassisSpeeds(
				balanceController.calculate(Math.sin(gyro.getRoll())),
				0,0,0
		);
		SmartDashboard.putNumber("currentCalc",balanceController.calculate(Math.sin(gyro.getRoll())));
		SmartDashboard.putBoolean("on", true);
		SmartDashboard.putNumber("delta", currentAngle - lastAngle);
	}
	
	@Override
	public boolean isFinished() {
		if(currentAngle - lastAngle <= -1 * minAngleChangeToStop){ //add min angle for stop
			SmartDashboard.putBoolean("on",false);
			return true;
		}
		lastAngle = Math.abs(gyro.getRoll());
		return false;
	}
	
	@Override
	public void end(boolean interrupted) {
		swerve.stop();
	}
}
