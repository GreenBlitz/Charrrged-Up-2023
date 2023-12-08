package edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystem;

import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos;

public class CurrentGriperNode {
    private static SystemsPos currentNode;
    public static SystemsPos getCurrentNode() {
        if (currentNode == null)
            currentNode = GRIPER_CLOSE;
        return currentNode;
    }
    public void setCurrentNode(SystemsPos pos) {
        currentNode = pos;
    }
}
