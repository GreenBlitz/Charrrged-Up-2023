package edu.greenblitz.tobyDetermined.commands.telescopicArm.moveByNodes;

import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState;

import java.util.function.Supplier;

public class MoveArmToNode extends GBCommand {
    Supplier<SystemsState> targetNode;

    public MoveArmToNode(Supplier<SystemsState> endNode) {
        this.targetNode = endNode;
    }

    @Override
    public void initialize() {
        new FullPath(targetNode.get()).schedule();
    }
}
