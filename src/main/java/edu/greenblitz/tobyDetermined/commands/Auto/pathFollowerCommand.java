package edu.greenblitz.tobyDetermined.commands.Auto;


import com.pathplanner.lib.PathPlannerTrajectory;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;

public class pathFollowerCommand extends SwerveControllerCommand {
	
	
	public pathFollowerCommand(PathPlannerTrajectory trajectory) {
		super(
				trajectory,
				SwerveChassis.getInstance()::getRobotPose,
				SwerveChassis.getInstance().getKinematics(),
				createPIDController(),
				createPIDController(),
				createThetaController(),
				SwerveChassis.getInstance()::setModuleStates,
				SwerveChassis.getInstance());
	}
	
	
	private static ProfiledPIDController createThetaController() {
		ProfiledPIDController thetaController = new ProfiledPIDController(
				RobotMap.Swerve.rotationPID.getKp(), 0, 0, new TrapezoidProfile.Constraints(RobotMap.Swerve.MAX_ANGULAR_SPEED, RobotMap.Swerve.MAX_ANGULAR_ACCELERATION));
		thetaController.enableContinuousInput(-Math.PI, Math.PI);
		return thetaController;
	}
	
	
	private static PIDController createPIDController() {
		return new PIDController(RobotMap.Swerve.SdsSwerve.linPID.getKp(), 0, 0);
	}
}

