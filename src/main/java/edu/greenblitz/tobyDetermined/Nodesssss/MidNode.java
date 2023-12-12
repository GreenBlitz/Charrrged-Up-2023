package edu.greenblitz.tobyDetermined.Nodesssss;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemConstants.*;

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
        if (start.toString().contains(systemName1))
            gbNode = getNodeBySystemName(systemName1);
        else
            gbNode = getNodeBySystemName(systemName2);
        gbNode.addNeighbors(new SystemsPos[]{start, end});
    }

    public GBNode getGBNode() {
        return gbNode;
    }

}
