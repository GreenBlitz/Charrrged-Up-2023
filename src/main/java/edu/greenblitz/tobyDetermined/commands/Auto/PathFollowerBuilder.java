package edu.greenblitz.tobyDetermined.commands.Auto;


import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.greenblitz.tobyDetermined.Field;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToPose;
import edu.greenblitz.tobyDetermined.commands.swerve.balance.bangBangBalance.FullBalance;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.EjectCube;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.GripCone;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.util.HashMap;

public class PathFollowerBuilder extends SwerveAutoBuilder {
	
	/**
	 * the use of the event map is to run a marker by its name run a command
	 */
	private static final HashMap<String, Command> eventMap = new HashMap<>();
	public static final double DEADLINE_TIME_FOR_PRE_AUTO_COMMAND = 1;
	
	//todo add commands to event map
	static {
		eventMap.put("FullConeHighAndReturn", new FullConeHighAndReturn());
		eventMap.put("PlaceFromAdjacentConeHigh", new PlaceFromAdjacent(RobotMap.TelescopicArm.PresetPositions.CONE_HIGH));
		eventMap.put("PlaceFromAdjacentCubeHigh", new PlaceFromAdjacent(RobotMap.TelescopicArm.PresetPositions.CUBE_HIGH));
		eventMap.put("DropCone", new GripCone());
		eventMap.put("DropCube", new EjectCube());
		eventMap.put("ArmToBelly", new PlaceFromAdjacent(RobotMap.TelescopicArm.PresetPositions.INTAKE_GRAB_POSITION));
		eventMap.put("MoveToPose8", new MoveToPose(Field.PlacementLocations.getLocationsOnBlueSide()[7], true));
		eventMap.put("MoveToPose2", new MoveToPose(Field.PlacementLocations.getLocationsOnBlueSide()[1], true));
		eventMap.put("MoveToOutRamp", new MoveToPose(Field.PlacementLocations.OUT_PRE_BALANCE_BLUE, true));
		eventMap.put("BalanceFromOut", new FullBalance(true));
		eventMap.put("BalanceFromIn", new FullBalance(false));
	}
	
	private static PathFollowerBuilder instance;
	
	private PathFollowerBuilder() {
		super(
				SwerveChassis.getInstance()::getRobotPose,
				(Pose2d pose) -> SwerveChassis.getInstance().resetChassisPose(pose),
				SwerveChassis.getInstance().getKinematics(),
				RobotMap.Swerve.Pegaswerve.TRANSLATION_PID,
				RobotMap.Swerve.Pegaswerve.ROTATION_PID,
				SwerveChassis.getInstance()::setModuleStates,
				eventMap,
				true,
				SwerveChassis.getInstance());
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
		
		PathPlannerTrajectory path;
		
		switch (RobotMap.ROBOT_NAME) {
			case pegaSwerve:
				path = PathPlanner.loadPath(
						pathName, new PathConstraints(
								RobotMap.Swerve.Pegaswerve.MAX_VELOCITY,
								RobotMap.Swerve.Pegaswerve.MAX_ACCELERATION
						));
				break;
			case Frankenstein:
				path = PathPlanner.loadPath(
						pathName, new PathConstraints(
								RobotMap.Swerve.TobyDetermined.MAX_VELOCITY,
								RobotMap.Swerve.TobyDetermined.MAX_ACCELERATION
						));
				break;
			default:
				path = PathPlanner.loadPath(
						pathName, new PathConstraints(
								RobotMap.Swerve.Pegaswerve.MAX_VELOCITY,
								RobotMap.Swerve.Pegaswerve.MAX_ACCELERATION
						));
				break;
		}
		
		if (path == null) {
			SmartDashboard.putString("wrong pth", pathName);
		}
		//pathplanner was acting wierd when starting position was not the one defined in the path so i added a reset to the path start
		return fullAuto(path).beforeStarting(new SetToFirstTrajectoryState(path).raceWith(new WaitCommand(DEADLINE_TIME_FOR_PRE_AUTO_COMMAND)));
	}
	
	public static PathPlannerTrajectory getPathPlannerTrajectory(String path) {
		return PathPlanner.loadPath(path, new PathConstraints(RobotMap.Swerve.Pegaswerve.MAX_VELOCITY, RobotMap.Swerve.Pegaswerve.MAX_ACCELERATION));
	}
	
}