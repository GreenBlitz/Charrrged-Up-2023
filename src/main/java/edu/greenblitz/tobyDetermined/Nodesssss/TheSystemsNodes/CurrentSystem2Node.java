package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;


public class CurrentSystem2Node {
    private static SystemsPos currentNode;
    public static SystemsPos getCurrentNode() {
        if (currentNode == null)
            currentNode = system2StartingNode;
        return currentNode;
    }
    public static void setCurrentNode(SystemsPos pos) {
        currentNode = pos;
    }
}
