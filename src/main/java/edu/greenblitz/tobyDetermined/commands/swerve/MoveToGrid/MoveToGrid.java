package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.greenblitz.tobyDetermined.commands.swerve.MoveToPos;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
/**
 * moves to a selected location from a list found in grid
 * using moveToPose
 * */
public class MoveToGrid extends MoveToPos {
    public MoveToGrid(){
        super(new Pose2d()); //will be overridden
    }

    @Override
    public void initialize() {
        pos = Grid.getInstance().getSelectedPosition();
        super.initialize();
    }
}
