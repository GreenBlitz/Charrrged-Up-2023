package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid.Grid;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;

public class GoToGrid extends InstantCommand {

	public GoToGrid() {
		super(() ->new GoToPosition(Grid.getInstance().getArmPositionByObject()).schedule());
	}


}