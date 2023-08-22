package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.Nodesssss.aStar;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.LinkedList;

public class NodeAllCommand extends GBCommand {
    NodeBase nodeBase;
    NodeArm start;
    NodeArm end;
    NodeCommand[] nodeCommands;
    public NodeAllCommand(NodeArm nodeArm, NodeArm end){
        nodeBase = NodeBase.getInstance();
        start = nodeArm;
        this.end = end;
    }

    @Override
    public void initialize() {
        LinkedList<NodeArm> path = aStar.getPath(start,end);
        assert path != null;
        nodeCommands = new NodeCommand[path.size()-1];
        for(int i = 0; i<path.size(); i++){
            nodeCommands[i] = new NodeCommand(path.get(i), path.get(i+1));
        }
    }

    @Override
    public void execute() {
            new SequentialCommandGroup(
                    nodeCommands[0],
                    nodeCommands[1],
                    nodeCommands[2]
            ).schedule();
    }

    @Override
    public boolean isFinished() {
        // if in point
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        //stop motors
        nodeBase.setCurrentNode(end);
    }
}

