package edu.greenblitz.tobyDetermined.commands.Auto;


import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.HashMap;

public class PathFollowerBuilder extends SwerveAutoBuilder {
	
	
	/**
	 * the use of the event map is to run a marker by its name run a command
	 */
	private static final HashMap<String, Command> eventMap = new HashMap<>();
	
	//todo add commands to event map
	static {
		// the event name, the command()
	}
	
	private static PathFollowerBuilder instance;
	
	private PathFollowerBuilder() {
		super(SwerveChassis.getInstance()::getRobotPose,
				(Pose2d pose) -> SwerveChassis.getInstance().resetChassisPose(pose),
				SwerveChassis.getInstance().getKinematics(),
				RobotMap.Swerve.SdsSwerve.linPID.getPIDConstants(),
				RobotMap.Swerve.rotationPID.getPIDConstants(),
				SwerveChassis.getInstance()::setModuleStates,
				eventMap,
				SwerveChassis.getInstance()
		);
	}
	
	
	public static PathFollowerBuilder getInstance() {
		if (instance == null) {
			instance = new PathFollowerBuilder();
		}
		return instance;
	}
	
	
	/**
	 * @return returns the command for the full auto (commands and trajectory included)
	 */
	public CommandBase followPath(String pathName) {
		
		return fullAuto(PathPlanner.loadPath(
				pathName,
				new PathConstraints(
						2,
						1
				))
		);
	}
	
	/**
	 * get the WPILib trajectory object from a .path file
	 */
	public Trajectory getTrajectory(String trajectory) {
		return PathPlanner.loadPath(trajectory,
				0.5,
				0.1);
	}
	
}