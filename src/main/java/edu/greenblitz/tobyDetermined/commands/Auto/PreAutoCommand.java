package edu.greenblitz.tobyDetermined.commands.Auto;

import com.pathplanner.lib.PathPlannerTrajectory;
import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.sql.Struct;

public class PreAutoCommand extends SwerveCommand {
	
	private final static double EPSILON = 0.0000001;
	private final static double moduleAngleTolerance = Units.degreesToRadians(1);
	
	PathPlannerTrajectory path;
	public PreAutoCommand (PathPlannerTrajectory trajectory){
		this.path= trajectory;
	}
	
	@Override
	public void initialize() {
		swerve.resetChassisPose(path.getInitialHolonomicPose());
	}
	
	@Override
	public void execute() {
		SmartDashboard.putBoolean("worky", true);
		PathPlannerTrajectory.PathPlannerState state = path.getInitialState();
		
		//make from the combined vector to leftward speed and forward speed
		double leftwardSpeed =
				state.poseMeters.getRotation().getSin() * state.velocityMetersPerSecond;
		double forwardSpeeeeed =
				state.poseMeters.getRotation().getCos() * state.velocityMetersPerSecond;
		ChassisSpeeds speeds = new ChassisSpeeds(forwardSpeeeeed *EPSILON, leftwardSpeed*EPSILON, state.holonomicAngularVelocityRadPerSec*EPSILON);
		swerve.moveByChassisSpeeds(speeds);
	}
	
	@Override
	public boolean isFinished() {
		boolean allInAngle = true;
		for (SwerveChassis.Module module : SwerveChassis.Module.values()){
			allInAngle &= swerve.moduleIsAtAngle(module,moduleAngleTolerance);
		}
		return allInAngle;
	}
	
	@Override
	public void end(boolean interrupted) {
		swerve.stop();
		SmartDashboard.putBoolean("worky", false);
	}
}
