package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.wpi.first.wpilibj.DriverStation;

public class SetSelectedTargetRight extends SetSelectedTarget {
    public SetSelectedTargetRight(DriverStation.Alliance alliance) {
        super(alliance, true);
    }
}
