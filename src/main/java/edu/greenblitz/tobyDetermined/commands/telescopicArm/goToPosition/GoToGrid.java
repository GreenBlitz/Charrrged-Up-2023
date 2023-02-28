package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class GoToGrid extends SequentialCommandGroup {
private static final RobotMap.TelescopicArm.PresetPositions[] presetPositions = RobotMap.TelescopicArm.PresetPositions.values();
	
	public GoToGrid(){
		for (RobotMap.TelescopicArm.PresetPositions presetPosition: presetPositions) {
			addCommands(new GoToPosition(presetPosition).unless( ()-> !(presetPosition == Grid.getInstance().getArmPositionByObject())));
		}
	}

	
}