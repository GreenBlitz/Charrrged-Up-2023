package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.Nodesssss.aStar;
import edu.greenblitz.tobyDetermined.subsystems.StraightLineMotion;
import edu.greenblitz.utils.GBCommand;

import java.util.LinkedList;

public class NodeAllCommand extends GBCommand {
    NodeBase nodeBase;
    NodeArm start;
    NodeArm end;
    NodeCommand[] nodeCommands;
    StraightLineMotion straightLineMotion;

    public NodeAllCommand( NodeArm end){
        nodeBase = NodeBase.getInstance();
        start = nodeBase.getCurrentNodeIndex();
        this.end = end;
        straightLineMotion = StraightLineMotion.getInstance();
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
        int i =0;
        while(i <= nodeCommands.length){ // problem
            nodeCommands[i].schedule();
            if(isFinished())
                i++;
        }
    }

    @Override
    public boolean isFinished() {
        return straightLineMotion.isInPlace(end);
    }

    @Override
    public void end(boolean interrupted) {
        nodeBase.setCurrentNode(end);
    }
}

