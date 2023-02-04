package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class SelectTargetLeft extends InstantCommand {
	int side;
	DriverStation.Alliance alliance;
	public SelectTargetLeft(DriverStation.Alliance alliance){
		this.alliance = alliance;
	}
	@Override
	public void initialize() {
		if (alliance == DriverStation.Alliance.Blue) {
			int side = 1;
		}
		if (alliance == DriverStation.Alliance.Red) {
			int side = -1;
		}
		int gridLength = Grid.Location.values().length;
		if (!(Grid.getInstance().getPose().ordinal() + side > gridLength || Grid.getInstance().getPose().ordinal() + side < 0)) {
			Grid.getInstance().setPose(Grid.Location.values()[Grid.getInstance().getPose().ordinal() + side]);
		}
	}

	@Override
	public boolean isFinished() {
		return true;
	}
	

}
