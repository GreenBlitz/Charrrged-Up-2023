package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.CurrentNodes;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPosition;


public class CurrentSystem2Node {
    private static SystemsPosition currentNode;
    public static SystemsPosition getCurrentNode() {
        if (currentNode == null)
            currentNode = system2StartingNode;
        return currentNode;
    }
    public static void setCurrentNode(SystemsPosition position) {
        currentNode = position;
    }
}
