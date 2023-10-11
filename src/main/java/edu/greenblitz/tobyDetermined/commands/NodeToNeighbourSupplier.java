package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.Nodesssss.AStar;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.ObjectPositionByNode;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.LinkedList;
import java.util.function.Supplier;

public class NodeToNeighbourSupplier implements Supplier<Command> {
    private RobotMap.TelescopicArm.PresetPositions start;
    private RobotMap.TelescopicArm.PresetPositions end;
    private GBCommand[] nodeCommands;
    public NodeToNeighbourSupplier(RobotMap.TelescopicArm.PresetPositions endNode) {
        end = endNode;
        SmartDashboard.putNumber("passIsFinished",0);
    }
    public Command get() {
        start = NodeBase.getInstance().getCurrentNode();
        SmartDashboard.putString("starting node", String.valueOf(start));
        SmartDashboard.putString("end node", String.valueOf(end));
        LinkedList<RobotMap.TelescopicArm.PresetPositions> path = AStar.getPath(start,end);
        nodeCommands = new GBCommand[path.size()];
        for(int i = 0; i<path.size()-2; i++) {
            nodeCommands[i] = new NodeToNeighbourCommand(path.get(i), path.get(i+1));
        }
        nodeCommands[path.size()-1] = new ObjectPositionByNode(NodeBase.getInstance().getNode(end).getClawPos());
        return new SequentialCommandGroup(nodeCommands);
    }

}
