package edu.greenblitz.tobyDetermined.Nodesssss.NonCollidingNodeSystem;

import edu.greenblitz.tobyDetermined.RobotMap;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemConstants.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemConstants.SystemsPos.*;

public class CurrentNode {
    private static SystemsPos currentNode;
    public static SystemsPos getCurrentNode() {
        if (currentNode == null)
            currentNode = system1StartingNode;
        return currentNode;
    }
    public static void setCurrentNode(SystemsPos pos) {
        currentNode = pos;
    }
}
