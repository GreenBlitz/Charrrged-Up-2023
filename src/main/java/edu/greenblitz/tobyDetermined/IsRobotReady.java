package edu.greenblitz.tobyDetermined;


import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

public class IsRobotReady {
	public static boolean isArmAtGoal(){
		switch (Grid.getInstance().getSelectedHeight()){
			case HIGH:
				if (Field.PlacementLocations.isGridPositionIDofCube(Grid.getInstance().getSelectedPositionID())){
					return ElbowSub.getInstance().isAtAngle(RobotMap.TelescopicArm.PresetPositions.CUBE_HIGH.angleInRadians)
							&& Extender.getInstance().isAtLength(RobotMap.TelescopicArm.PresetPositions.CUBE_HIGH.distance);
				}
				else{
					return ElbowSub.getInstance().isAtAngle(RobotMap.TelescopicArm.PresetPositions.CONE_HIGH.angleInRadians)
							&& Extender.getInstance().isAtLength(RobotMap.TelescopicArm.PresetPositions.CONE_HIGH.distance);
				}
			case MEDIUM:
				if (Field.PlacementLocations.isGridPositionIDofCube(Grid.getInstance().getSelectedPositionID())){
					return ElbowSub.getInstance().isAtAngle(RobotMap.TelescopicArm.PresetPositions.CUBE_MID.angleInRadians)
							&& Extender.getInstance().isAtLength(RobotMap.TelescopicArm.PresetPositions.CUBE_MID.distance);
				}
				else{
					return ElbowSub.getInstance().isAtAngle(RobotMap.TelescopicArm.PresetPositions.CONE_MID.angleInRadians)
							&& Extender.getInstance().isAtLength(RobotMap.TelescopicArm.PresetPositions.CONE_MID.distance);
				}
			case LOW:
				return ElbowSub.getInstance().isAtAngle(RobotMap.TelescopicArm.PresetPositions.LOW.angleInRadians)
						&& Extender.getInstance().isAtLength(RobotMap.TelescopicArm.PresetPositions.LOW.distance);
		}
		return false;
	}

	public static boolean isChassisAtGoal(){
		return SwerveChassis.getInstance().isAtPose(Grid.getInstance().getSelectedPosition());
	}

	public static boolean isRobotReady(){
		return isArmAtGoal() && isChassisAtGoal();
	}
}