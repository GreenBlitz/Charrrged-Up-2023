package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.MidNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.GBNode;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.system2StartingNode;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.getNodeBySystemName;

public class System2MidNode {

    private GBNode midNode;
    private static System2MidNode instance;

    private System2MidNode() {
        setNewMidNode(system2StartingNode, system2StartingNode);
    }

    public static System2MidNode getInstance() {
        if (instance == null)
            instance = new System2MidNode();
        return instance;
    }

    public void setNewMidNode(NodeBase.SystemsPosition start, NodeBase.SystemsPosition end) {
        if (start.equals(NodeBase.SystemsPosition.MID_NODE))
            start = midNode.getNeighbors().get(0);
        midNode = getNodeBySystemName(start);
        midNode.addNeighbors(new NodeBase.SystemsPosition[]{start, end});
    }

    public GBNode getGBNode() {
        return midNode;
    }

}
