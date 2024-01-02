package edu.greenblitz.tobyDetermined.commands.telescopicArm.moveByNodes;

import edu.greenblitz.utils.GBCommand;
import java.util.function.Supplier;
import edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.SystemsState;

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
