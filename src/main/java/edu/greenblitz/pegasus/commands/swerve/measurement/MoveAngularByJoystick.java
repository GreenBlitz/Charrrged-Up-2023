package edu.greenblitz.pegasus.commands.swerve.measurement;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.swerve.SwerveCommand;
import edu.greenblitz.pegasus.utils.hid.SmartJoystick;
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
		SmartDashboard.putNumber("max lin speed", maxSpeed*RobotMap.Pegasus.Swerve.SwerveLocationsInSwerveKinematicsCoordinates[0].getNorm());
	}
	
}
