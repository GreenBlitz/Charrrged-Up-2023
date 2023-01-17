package edu.greenblitz.tobyDetermined.commands.Auto;


import com.pathplanner.lib.*;

import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class PathFollowerBuilder extends SwerveAutoBuilder{
    private static final HashMap<String, Command> eventMap = new HashMap<>();
	static {
		// the event name, the command()
    }


	
	private static PathFollowerBuilder instance;

        private PathFollowerBuilder() {
            super(SwerveChassis.getInstance()::getlocation,
                    SwerveChassis.getInstance()::resetLocalizer,
                    SwerveChassis.getInstance().getKinematics(),
                    RobotMap.Swerve.SdsSwerve.linPID.getPIDConst(),
                    RobotMap.Swerve.SdsSwerve.angPID.getPIDConst(),
                    SwerveChassis.getInstance()::setModuleStates,
                    eventMap,
                    SwerveChassis.getInstance()
            );}
    
	
	
	
	public static PathFollowerBuilder getInstance() {
		if (instance == null) {
			instance = new PathFollowerBuilder();
		}
		return instance;
	}
	

	/**
	 * @return returns the command for the full auto (commands and trajectory included)
	 * */
	public CommandBase followPath(String pathName) {
		
		return fullAuto(PathPlanner.loadPath(
				pathName,
				new PathConstraints(
						4,
						1
				))
		);
	}

	/** get the WPILib trajectory object from a .path file */
	public Trajectory getTrajectory (String trajectory){
		return PathPlanner.loadPath(trajectory,
				4,
				1);
	}

}