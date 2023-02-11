package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public abstract class SetSelectedTarget extends InstantCommand {
    DriverStation.Alliance alliance;
    private int sideMultiplier;
    public SetSelectedTarget(DriverStation.Alliance alliance, boolean moveRight) {
        this.alliance = alliance;
        sideMultiplier = moveRight ? 1 : -1;
    }

    @Override
    public void initialize() {
        int allianceSideMultiplier = 0;
        if (alliance == DriverStation.Alliance.Blue) {
            allianceSideMultiplier = -1;
        } else if (alliance == DriverStation.Alliance.Red) {
            allianceSideMultiplier = 1;
        }
        int side = allianceSideMultiplier * sideMultiplier;
		int gridLength = Grid.getInstance().Location.length;
        if (!(Grid.getInstance().getSelectedPositionID() + side >= gridLength || Grid.getInstance().getSelectedPositionID() + side < 0)) {
			Grid.getInstance().setSelectedPosition(Grid.getInstance().getSelectedPositionID() + side);
		}
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}

