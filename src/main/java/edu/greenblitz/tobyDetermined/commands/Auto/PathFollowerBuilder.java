package edu.greenblitz.tobyDetermined.commands.Auto;


import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.swerve.SwerveCommand;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.utils.PIDObject;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;


import java.sql.Time;
import java.util.HashMap;

public class PathFollowerBuilder extends SwerveAutoBuilder {
	
	
	
	public static final PIDObject translationPID = new PIDObject().withKp(0);
	public static final PIDObject rotationPID = new PIDObject().withKp(2).withKi(0);
	
	/**
	 * the use of the event map is to run a marker by its name run a command
	 */
	private static final HashMap<String, Command> eventMap = new HashMap<>();
	
	//todo add commands to event map
	static {
		// the event name, the command()
		eventMap.put("place",new InstantCommand(){
			@Override
			public void initialize() {
				SmartDashboard.putBoolean("place" ,true);
				Timer.delay(2);
			}
			
			
			@Override
			public void end(boolean interrupted) {
				SmartDashboard.putBoolean("place" ,false);
			}
		});
		eventMap.put("intake",new InstantCommand(){
			@Override
			public void initialize() {
				SmartDashboard.putBoolean("intake" ,true);
			}
			
			
			@Override
			public void end(boolean interrupted) {
				SmartDashboard.putBoolean("intake" ,false);
			}
		});
		eventMap.put("stopIntake",new InstantCommand(){
			@Override
			public void initialize() {
				SmartDashboard.putBoolean("stopIntake" ,true);
			}
			
			
			@Override
			public void end(boolean interrupted) {
				SmartDashboard.putBoolean("stopIntake" ,false);
			}
		});
		eventMap.put("processIntake",new InstantCommand(){
			@Override
			public void initialize() {
				SmartDashboard.putBoolean("processes" ,true);
			}
			
			@Override
			public void end(boolean interrupted) {
				SmartDashboard.putBoolean("processes" ,false);
			}
		});
	}
	
	private static PathFollowerBuilder instance;
	
	private PathFollowerBuilder() {
		super(SwerveChassis.getInstance()::getRobotPose,
				(Pose2d pose) -> SwerveChassis.getInstance().resetChassisPose(pose),
				SwerveChassis.getInstance().getKinematics(),
				translationPID.getPIDConstants(),
				rotationPID.getPIDConstants(),
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

		PathPlannerTrajectory path = PathPlanner.loadPath(
				pathName,
				new PathConstraints(
						RobotMap.Swerve.Pegaswerve.MAX_VELOCITY,
						RobotMap.Swerve.Pegaswerve.MAX_ACCELERATION
				));
		//pathplanner was acting wierd when starting position was not the one defined in the path so i added a reset to the path start
		return fullAuto(path).beforeStarting(new PreAutoCommand(path).raceWith(new WaitCommand(1.5)));
	}
	
	public static PathPlannerTrajectory getPathPlannerTrajectory(String path){
		return PathPlanner.loadPath(
				path,
				new PathConstraints(
						RobotMap.Swerve.Pegaswerve.MAX_VELOCITY,
						RobotMap.Swerve.Pegaswerve.MAX_ACCELERATION
				));
	}
	
}