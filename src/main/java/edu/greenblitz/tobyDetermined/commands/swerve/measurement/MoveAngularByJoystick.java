package edu.greenblitz.tobyDetermined.commands.swerve.measurement;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveAngularByJoystick extends SwerveCommand {
	
	public final SmartJoystick joystick;
	
	public MoveAngularByJoystick(SmartJoystick joystick) {
		this.joystick = joystick;
	}
	
	@Override
	public void initialize() {
		super.initialize();
		SmartDashboard.putNumber("max ang speed", 0);
	}
	
	public void execute() {
		double angularSpeed = -joystick.getAxisValue(SmartJoystick.Axis.RIGHT_X);
		if (angularSpeed == 0) {
			swerve.stop();
			return;
		}
		swerve.rotateChassisByPower(angularSpeed);
		double maxSpeed = Math.max(Math.abs(swerve.getChassisSpeeds().omegaRadiansPerSecond), SmartDashboard.getNumber("max ang speed", 0));
		SmartDashboard.putNumber("max ang speed", maxSpeed);

		switch (RobotMap.robotName){
			case pegaSwerve:
				SmartDashboard.putNumber("max lin speed", maxSpeed * RobotMap.Swerve.Pegaswerve.SwerveLocationsInSwerveKinematicsCoordinates[0].getNorm());
			case TobyDetermined:
				SmartDashboard.putNumber("max lin speed", maxSpeed * RobotMap.Swerve.TobyDetermined.SwerveLocationsInSwerveKinematicsCoordinates[0].getNorm());

		}

	}
	
}
