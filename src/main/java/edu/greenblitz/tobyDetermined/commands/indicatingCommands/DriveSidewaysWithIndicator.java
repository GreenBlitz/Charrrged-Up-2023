package edu.greenblitz.tobyDetermined.commands.indicatingCommands;

import edu.greenblitz.tobyDetermined.commands.swerve.DriveSidewaysUntilEdge;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;

public class DriveSidewaysWithIndicator extends ParallelRaceGroup {

    public DriveSidewaysWithIndicator(DriveSidewaysUntilEdge.Direction direction,double speed){
        super(new DriveSidewaysUntilEdge(direction,speed));
    }





}
