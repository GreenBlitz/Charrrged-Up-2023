package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.Nodesssss.AStar;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.ObjectPositionByNode;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.LinkedList;
import java.util.function.Supplier;

public class NodeToNeighbourSupplier implements Supplier<Command> {
    private NodeArm start;
    private NodeArm end;
    private GBCommand[] nodeCommands;
    public NodeToNeighbourSupplier(NodeArm endNode) {
        end = endNode;
        SmartDashboard.putNumber("passIsFinished",0);
    }
    public Command get() {
        start = NodeBase.getInstance().getCurrentNode();
        SmartDashboard.putNumber("starting node",start.getId());
        SmartDashboard.putNumber("end node",end.getId());
        LinkedList<NodeArm> path = AStar.getPath(start,end);
        nodeCommands = new GBCommand[path.size()];
        for(int i = 0; i<path.size()-2; i++) {
            nodeCommands[i] = new NodeToNeighbourCommand(path.get(i), path.get(i + 1));
        }
        nodeCommands[path.size()-1] = new ObjectPositionByNode(end.getClawPos());
        return new SequentialCommandGroup(nodeCommands);
    }

}
