package edu.greenblitz.tobyDetermined.commands.telescopicArm.moveByNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState;
import edu.greenblitz.tobyDetermined.Nodesssss.NonCollidingNodeSystemAlgorithm.AStar;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.LinkedList;
import java.util.function.Supplier;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.CreateCurrents.system1CurrentNode;

public class GeneratePath implements Supplier<Command> {

    private final SystemsState targetNode;

    public GeneratePath(SystemsState endNode) {
        targetNode = endNode;
    }

    public Command get() {
        SystemsState start = system1CurrentNode.getCurrentNode();
        LinkedList<SystemsState> path = AStar.getPath(start, targetNode);
        GBCommand[] nodeCommands = new GBCommand[path.size() - 1];
        for (int i = 0; i < path.size() - 1; i++) {
            nodeCommands[i] = new StraightLineMotion(path.get(i), path.get(i + 1));
        }
        return new SequentialCommandGroup(nodeCommands);
    }

}
