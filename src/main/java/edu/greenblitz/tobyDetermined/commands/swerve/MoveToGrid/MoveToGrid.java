package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.greenblitz.tobyDetermined.commands.swerve.MoveToPos;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;

public class MoveToGrid extends MoveToPos {
    public MoveToGrid(){
        super(new Pose2d()); //will be overriden
    }
    @Override
    public void initialize() {
        pos = Grid.getInstance().getLocation().getPose2D(DriverStation.getAlliance());
        super.initialize();
    }
}
