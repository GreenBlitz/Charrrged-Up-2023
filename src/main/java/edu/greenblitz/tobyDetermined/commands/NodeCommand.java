package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.subsystems.StraightLineMotion;
import edu.greenblitz.utils.GBCommand;

public class NodeCommand extends GBCommand {
    double gamma;
    private final StraightLineMotion straightLineMotion;
    NodeArm start;
    NodeArm end;

    public NodeCommand(NodeArm start, NodeArm end){
        straightLineMotion = StraightLineMotion.getInstance();
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute() {
        if(start.getNeighbors().contains(end)) {
            straightLineMotion.moveArm(3, end, gamma);//gamma problem
        }
    }

    @Override
    public boolean isFinished() {
        return straightLineMotion.isInPlace(end);
    }


    public void end(boolean interrupted) {
        straightLineMotion.stopMotors();
    }
    // delete maybe because of time wast

}
