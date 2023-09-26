package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.Nodesssss.AStar;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.LinkedList;
import java.util.function.Supplier;

public class NodeToNeighbourSupplier implements Supplier<Command> {
    private NodeArm start;
    private NodeArm end;
    private NodeToNeighbourCommand[] nodeCommands;
    public NodeToNeighbourSupplier(NodeArm endNode) {
        end = endNode;
        SmartDashboard.putNumber("passIsFinished",0);
    }
    public Command get() {
        start = NodeBase.getInstance().getCurrentNode();
        SmartDashboard.putNumber("starting node",start.getId());
        SmartDashboard.putNumber("end node",end.getId());
        LinkedList<NodeArm> path = AStar.getPath(start,end);
        assert path != null;//can delete
        if (path == null)//can delete
            return null;//can delete
        nodeCommands = new NodeToNeighbourCommand[path.size()-1];
        for(int i = 0; i<path.size()-1; i++) {
            nodeCommands[i] = new NodeToNeighbourCommand(path.get(i), path.get(i + 1));
        }
        return new SequentialCommandGroup(nodeCommands);
    }

}
