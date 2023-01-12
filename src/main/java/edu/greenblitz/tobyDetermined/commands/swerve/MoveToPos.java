package edu.greenblitz.tobyDetermined.commands.swerve;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;

public class MoveToPos extends GBCommand{
	private ProfiledPIDController xController;
	private ProfiledPIDController yController;
	private ProfiledPIDController rotationController;

	public MoveToPos(Pose2d pos){
		this.xController = new ProfiledPIDController(RobotMap.Swerve.Autonomus.translationKp, RobotMap.Swerve.Autonomus.translationKi, RobotMap.Swerve.Autonomus.translationKd, RobotMap.Swerve.Autonomus.constraints);
		xController.setGoal(pos.getX());
		this.yController = new ProfiledPIDController(RobotMap.Swerve.Autonomus.translationKp, RobotMap.Swerve.Autonomus.translationKi, RobotMap.Swerve.Autonomus.translationKd, RobotMap.Swerve.Autonomus.constraints);
		yController.setGoal(pos.getY());
		this.rotationController = new ProfiledPIDController(RobotMap.Swerve.Autonomus.rotKp, RobotMap.Swerve.Autonomus.rotKi, RobotMap.Swerve.Autonomus.rotKd, RobotMap.Swerve.Autonomus.constraints);
		rotationController.setGoal(pos.getRotation().getRadians());
		rotationController.enableContinuousInput(0, 2*Math.PI);
	}

	@Override
	public void execute() {
		SwerveChassis.getInstance().moveByChassisSpeeds(
				xController.calculate(SwerveChassis.getInstance().getRobotPose().getX()),
				yController.calculate(SwerveChassis.getInstance().getRobotPose().getY()),
				rotationController.calculate(SwerveChassis.getInstance().getRobotPose().getRotation().getRadians()),
				SwerveChassis.getInstance().getChassisAngle()
		);
	}
}
