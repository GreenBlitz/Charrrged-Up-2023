package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.MultiNodeSubsystem.AStarVertex;
import edu.greenblitz.tobyDetermined.Nodesssss.MultiNodeSubsystem.CurrentGriperNode;
import edu.greenblitz.tobyDetermined.Nodesssss.MultiNodeSubsystem.CurrentNodeArm;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.LinkedList;
import java.util.function.Supplier;

public class NodeToNeighbourSupplier implements Supplier<Command> {

    private RobotMap.NodeSystem.SystemsPos end;
    private GBCommand[] nodeCommands;
    public RobotMap.NodeSystem.SystemsPos getEnd(){
        return end;
    }

    public NodeToNeighbourSupplier(RobotMap.NodeSystem.SystemsPos endNode) {
        end = endNode;
        SmartDashboard.putNumber("passIsFinished",0);
    }
    public Command get() {
        RobotMap.NodeSystem.SystemsPos start = CurrentNodeArm.getCurrentNode();
        LinkedList<RobotMap.NodeSystem.SystemsPos> path = AStarVertex.getPath(start,end, CurrentGriperNode.getCurrentNode()).getFirst();
        nodeCommands = new GBCommand[path.size()-1];
        for(int i = 0; i<path.size()-1; i++) {
            if (path.get(i).toString().contains("ARM") && path.get(i+1).toString().contains("ARM"))
                nodeCommands[i] = new NodeToNeighbourCommand(path.get(i),path.get(i+1));
            else if(path.get(i).toString().contains("GRIPER") && path.get(i+1).toString().contains("GRIPER"))
                nodeCommands[i] = new GriperCommand();

        }
        return new SequentialCommandGroup(nodeCommands);
        //.andThen( ObjectPositionByNode.getCommandFromState(NodeBase.getNode(end).getClawPos()));
    }

}
