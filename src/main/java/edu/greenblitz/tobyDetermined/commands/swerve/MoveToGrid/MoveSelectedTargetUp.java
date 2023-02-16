package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class MoveSelectedTargetUp extends InstantCommand {
    @Override
    public void initialize() {
        Grid.getInstance().moveSelectedHeightUp();
    }
}
