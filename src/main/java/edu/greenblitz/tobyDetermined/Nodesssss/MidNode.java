package edu.greenblitz.tobyDetermined.Nodesssss;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemFunctions.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;


public class MidNode {
    private GBNode gbNode;
    private static MidNode instance;

    private MidNode() {
        gbNode = null;
    }

    public static MidNode getInstance() {
        if (instance == null)
            instance = new MidNode();
        return instance;
    }

    public void setNewMidNode(SystemsPos start, SystemsPos end) {
        if (start.equals(SystemsPos.MID_NODE))
            start = gbNode.getNeighbors().get(0);
        gbNode = getNodeBySystemName(start);
        gbNode.addNeighbors(new SystemsPos[]{start, end});
    }

    public GBNode getGBNode() {
        return gbNode;
    }

}
