package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.greenblitz.tobyDetermined.RobotMap;

public class GoToWallZone extends SimpleGoToPosition{

	protected GoToWallZone() {
		super(RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH - (RobotMap.TelescopicArm.Extender.FORWARDS_LENGTH_TOLERANCE), (RobotMap.TelescopicArm.Elbow.END_WALL_ZONE_ANGLE+RobotMap.TelescopicArm.Elbow.STARTING_WALL_ZONE_ANGLE) /2);
	}
}
