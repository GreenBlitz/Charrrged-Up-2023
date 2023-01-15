package edu.greenblitz.tobyDetermined.commands.swerve.measurement;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.utils.PigeonGyro;
import edu.wpi.first.math.controller.PIDController;

public class BalanceOnRampBangBang extends SwerveCommand { //hisstersis stop
	private final PigeonGyro gyro;
	private final PIDController balancePIDController;
	private boolean hasPassedTippingPoint = false;
	private boolean hasPassedMidPoint = false;
	
	
	public BalanceOnRampBangBang (){
		balancePIDController = RobotMap.Swerve.balancePID.getPIDController();
		gyro = swerve.getPigeonGyro();
		balancePIDController.setSetpoint(0);
	}
	
	@Override
	public void execute() { //assumes front is perpendicular
		double velocity =balancePIDController.calculate(Math.sin(gyro.getRoll()));
		
		if(gyro.getRoll() > 14){
			hasPassedMidPoint = true;
		}
		
		if(hasPassedMidPoint && gyro.getRoll() < 12){
			hasPassedTippingPoint = true;
			swerve.stop();
		}
		
		if (!hasPassedMidPoint){
		
		}
	}
}
