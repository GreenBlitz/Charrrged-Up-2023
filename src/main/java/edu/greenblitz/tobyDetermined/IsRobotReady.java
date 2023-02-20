package edu.greenblitz.tobyDetermined;


import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.greenblitz.tobyDetermined.subsystems.swerve.SwerveChassis;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;

public class IsRobotReady {
	public boolean isArmReady(){
		switch (Grid.getInstance().getSelectedHeight()){
			case HIGH:
				return RobotMap.TelescopicArm.PresetPositions.CONE_HIGH.angleInRadians - Elbow.getInstance().getAngle() <= RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE
						&& RobotMap.TelescopicArm.PresetPositions.CONE_HIGH.distance -  Extender.getInstance().getLength() <= RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE
						|| RobotMap.TelescopicArm.PresetPositions.CUBE_HIGH.angleInRadians - Elbow.getInstance().getAngle() <= RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE
						&& RobotMap.TelescopicArm.PresetPositions.CUBE_HIGH.distance -  Extender.getInstance().getLength() <= RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE;
			case MEDIUM:
				return RobotMap.TelescopicArm.PresetPositions.CONE_MID.angleInRadians - Elbow.getInstance().getAngle() <= RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE
						&& RobotMap.TelescopicArm.PresetPositions.CONE_MID.distance -  Extender.getInstance().getLength() <= RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE
						|| RobotMap.TelescopicArm.PresetPositions.CUBE_MID.angleInRadians - Elbow.getInstance().getAngle() <= RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE
						&& RobotMap.TelescopicArm.PresetPositions.CUBE_MID.distance -  Extender.getInstance().getLength() <= RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE;
			case LOW:
				return RobotMap.TelescopicArm.PresetPositions.LOW.angleInRadians - Elbow.getInstance().getAngle() <= RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE
						&& RobotMap.TelescopicArm.PresetPositions.LOW.distance -  Extender.getInstance().getLength() <= RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE;
		}
		return false;
	}

	public boolean isAtGoal(){
		boolean isAtX = Grid.getInstance().getSelectedPosition().getX() - SwerveChassis.getInstance().getRobotPose().getX() <= RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE;
		boolean isAtY = Grid.getInstance().getSelectedPosition().getX() - SwerveChassis.getInstance().getRobotPose().getY() <= RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE;
		boolean isAtAngle = Grid.getInstance().getSelectedPosition().getRotation().getRadians() - SwerveChassis.getInstance().getRobotPose().getRotation().getRadians() <= RobotMap.TelescopicArm.Elbow.ANGLE_TOLERANCE;

		return isAtX && isAtY && isAtAngle;
	}

	public boolean isRobotReady(){
		return isArmReady() && isAtGoal();
	}
}