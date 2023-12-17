package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.MidNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.GBNode;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.system1StartingNode;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPosition;


public class System1MidNode {
    private GBNode midNode;
    private static System1MidNode instance;

    private System1MidNode() {
        setNewMidNode(system1StartingNode, system1StartingNode);
    }

    public static System1MidNode getInstance() {
        if (instance == null)
            instance = new System1MidNode();
        return instance;
    }

    public void setNewMidNode(SystemsPosition start, SystemsPosition end) {
        if (start.equals(SystemsPosition.MID_NODE))
            start = midNode.getNeighbors().get(0);
        midNode = getNodeBySystemName(start);
        midNode.addNeighbors(new SystemsPosition[]{start, end});
    }

    public GBNode getMidNode() {
        return midNode;
    }

}
