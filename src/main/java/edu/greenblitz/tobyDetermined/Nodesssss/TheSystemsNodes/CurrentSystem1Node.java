package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;


public class CurrentSystem1Node {
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
