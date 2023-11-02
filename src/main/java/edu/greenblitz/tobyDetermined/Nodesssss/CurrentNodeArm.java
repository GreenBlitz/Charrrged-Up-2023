package edu.greenblitz.tobyDetermined.Nodesssss;

import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos;
import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem;

public class CurrentNodeArm {
    private static SystemsPos currentNode;
    public static SystemsPos getCurrentNode() {
        if (currentNode == null)
            currentNode = ARM_MID;
        return currentNode;
    }
    public static void setCurrentNode(SystemsPos pos) {
        currentNode = pos;
    }
}
