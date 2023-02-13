package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class MoveSelectedTargetRight extends InstantCommand {
    @Override
    public void initialize() {
        Grid.getInstance().moveSelectedPositionRight();
    }
}
