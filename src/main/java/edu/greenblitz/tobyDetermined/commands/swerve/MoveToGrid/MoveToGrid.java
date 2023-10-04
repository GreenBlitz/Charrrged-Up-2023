package edu.greenblitz.tobyDetermined.commands.swerve.MoveToGrid;

import edu.greenblitz.tobyDetermined.commands.swerve.MoveToPose;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * moves to a selected location from a list found in grid
 * using moveToPose
 * */
public class MoveToGrid extends MoveToPose {

    static int cnt = 0;


    public MoveToGrid(){
        super(new Pose2d()); //will be overridden
        cnt++;
    }

    @Override
    public void initialize() {
        SmartDashboard.putNumber("mtp counter", cnt);
        pose = Grid.getInstance().getSelectedPosition();
        super.initialize();
    }
}
