package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.swerve.AngPIDSupplier;
import edu.greenblitz.tobyDetermined.commands.swerve.MoveToPos;
import edu.wpi.first.wpilibj.DriverStation;

public class MoveToGrid extends MoveToPos {
    public MoveToGrid() {
        super(Grid.getInstance().getPose().getPose(DriverStation.Alliance.Blue));
    }
}
