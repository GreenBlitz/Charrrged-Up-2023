package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.*;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.ObjectPositionByNode;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.LinkedList;
import java.util.function.Supplier;

public class NodeToNeighbourSupplier implements Supplier<Command> {
    private static RobotMap.NodeSystem.SystemsPos end;
    private GBCommand[] nodeCommands;
    public static RobotMap.NodeSystem.SystemsPos getEnd(){
        return end;
    }

    public NodeToNeighbourSupplier(RobotMap.NodeSystem.SystemsPos endNode) {
        end = endNode;
        SmartDashboard.putNumber("passIsFinished",0);
    }
    public Command get() {
        RobotMap.NodeSystem.SystemsPos start = CurrentNodeArm.getCurrentNode();
        LinkedList<RobotMap.NodeSystem.SystemsPos> path = AStarVertex.getPath(start, end, CurrentGriperNode.getCurrentNode()).getFirst();
        nodeCommands = new GBCommand[path.size()-1];
        for(int i = 0; i<path.size()-1; i++) {
            nodeCommands[i] = new NodeToNeighbourCommand(path.get(i+1));
        }
        NodeArm nodeArm = (NodeArm) NodeBase.getNode(end);
        return new SequentialCommandGroup(nodeCommands).andThen(ObjectPositionByNode.getCommandFromState(nodeArm.getClawPos()));
    }

}
