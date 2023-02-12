package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.greenblitz.tobyDetermined.commands.swerve.MoveToPose;
import edu.wpi.first.math.geometry.Pose2d;

/**
 * moves to a selected location from a list found in grid
 * using moveToPose
 * */
public class MoveToGrid extends MoveToPose {
    public MoveToGrid(){
        super(new Pose2d()); //will be overridden
    }

    @Override
    public void initialize() {
        pose = Grid.getInstance().getSelectedPosition();
        super.initialize();
    }
}
