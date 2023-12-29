package edu.greenblitz.tobyDetermined.commands.telescopicArm.moveByNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState;
import edu.greenblitz.tobyDetermined.Nodesssss.NonCollidingNodeSystemAlgorithm.AStar;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.LinkedList;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.CreateCurrents.system1CurrentNode;

public class PathGenerator implements Supplier<Command> {

    private final SystemsState targetNode;

    public PathGenerator(SystemsState endNode) {
        targetNode = endNode;
    }

    public Command get() {
        SystemsState start = system1CurrentNode.getCurrentNode();
        LinkedList<SystemsState> path = AStar.getPath(start, targetNode);
        return new SequentialCommandGroup(
                IntStream.range(0, path.size() - 1).mapToObj(i -> new StraightLineMotion(path.get(i), path.get(i + 1))).toArray(GBCommand[]::new)
        );
    }

}
