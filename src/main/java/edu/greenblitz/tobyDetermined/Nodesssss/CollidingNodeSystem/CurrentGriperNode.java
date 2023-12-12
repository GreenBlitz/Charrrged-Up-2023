package edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystem;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemConstants.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemConstants.SystemsPos.*;

public class CurrentGriperNode {
    private static SystemsPos currentNode;
    public static SystemsPos getCurrentNode() {
        if (currentNode == null)
            currentNode = system2StartingNode;
        return currentNode;
    }
    public void setCurrentNode(SystemsPos pos) {
        currentNode = pos;
    }
}
