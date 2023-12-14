package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.Currents;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.system3StartingNode;

public class CurrentSystem3Node {
    private static NodeBase.SystemsPos currentNode;
    public static NodeBase.SystemsPos getCurrentNode() {
        if (currentNode == null)
            currentNode = system3StartingNode;
        return currentNode;
    }
    public static void setCurrentNode(NodeBase.SystemsPos pos) {
        currentNode = pos;
    }
}
