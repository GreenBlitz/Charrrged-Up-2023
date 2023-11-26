package edu.greenblitz.tobyDetermined.commands.Auto;

import com.pathplanner.lib.PathPlannerTrajectory;
import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.tobyDetermined.subsystems.swerve.Chassis.SwerveChassis;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.List;

//align weals to the first auto state
public class SetToFirstTrajectoryState extends SwerveCommand {

	private final static double EPSILON = 0.00001;
	private final static double MODULE_ANGLE_TOLERANCE = Units.degreesToRadians(4);

	PathPlannerTrajectory path;

	public SetToFirstTrajectoryState(PathPlannerTrajectory trajectory) {
		this.path = trajectory;
	}

	public SetToFirstTrajectoryState(List<PathPlannerTrajectory> trajectory) {
		this(trajectory.get(0));
	}

	@Override
	public void initialize() {
		swerve.resetChassisPose(path.getInitialHolonomicPose());
	}

	@Override
	public void execute() {
		SmartDashboard.putBoolean("pre auto command", true);
		PathPlannerTrajectory.PathPlannerState state = path.getInitialState();

		//make from the combined vector to leftward speed and forward speed
		double leftwardSpeed =
				state.poseMeters.getRotation().getSin() * state.velocityMetersPerSecond;
		double forwardSpeeeeed =
				state.poseMeters.getRotation().getCos() * state.velocityMetersPerSecond;
		ChassisSpeeds speeds = new ChassisSpeeds(forwardSpeeeeed * EPSILON, leftwardSpeed * EPSILON, state.holonomicAngularVelocityRadPerSec * EPSILON);
		swerve.moveByChassisSpeeds(speeds);
	}

	@Override
	public boolean isFinished() {
		boolean allInAngle = true;
		for (SwerveChassis.Module module : SwerveChassis.Module.values()) {
			allInAngle &= swerve.isModuleAtAngle(module, MODULE_ANGLE_TOLERANCE);
		}
		return allInAngle;
	}

	@Override
	public void end(boolean interrupted) {
		swerve.stop();
		SmartDashboard.putBoolean("pre auto command", false);
	}
}
