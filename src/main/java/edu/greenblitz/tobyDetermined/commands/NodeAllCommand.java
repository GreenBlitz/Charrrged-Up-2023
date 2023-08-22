package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.Nodesssss.aStar;
import edu.greenblitz.utils.GBCommand;

import java.util.LinkedList;

public class NodeAllCommand extends GBCommand {
    NodeCommand nodeCommand;
    NodeBase nodeBase;
    int start;
    int end;
    public NodeAllCommand(NodeArm nodeArm, int end){
        nodeBase = NodeBase.getInstance();
        start = nodeArm.getId();
        this.end = end;
    }

    @Override
    public void execute() {
        LinkedList<Integer> path = aStar.getPath(nodeBase.getNode(start),nodeBase.getNode(end));
        for (int i = 0; i<path.size(); i++){
            //path.get(i)
        }
    }
}
