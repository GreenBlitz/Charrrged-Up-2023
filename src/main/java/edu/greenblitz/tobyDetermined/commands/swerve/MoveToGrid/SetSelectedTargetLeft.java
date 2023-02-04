package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.wpi.first.wpilibj.DriverStation;

public class SetSelectedTargetLeft extends SetSelectedTarget {
    public SetSelectedTargetLeft(DriverStation.Alliance alliance) {
        super(alliance, false);
    }
}
