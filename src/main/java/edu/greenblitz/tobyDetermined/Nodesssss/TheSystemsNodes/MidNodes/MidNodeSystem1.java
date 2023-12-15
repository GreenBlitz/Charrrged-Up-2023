package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.MidNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.GBNode;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.system1StartingNode;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;


public class MidNodeSystem1 {
    private GBNode midNode;
    private static MidNodeSystem1 instance;

    private MidNodeSystem1() {
        setNewMidNode(system1StartingNode, system1StartingNode);
    }

    public static MidNodeSystem1 getInstance() {
        if (instance == null)
            instance = new MidNodeSystem1();
        return instance;
    }

    public void setNewMidNode(SystemsPos start, SystemsPos end) {
        if (start.equals(SystemsPos.MID_NODE))
            start = midNode.getNeighbors().get(0);

        midNode = getNodeBySystemName(start);

        midNode.setOtherSystemMustBeToOut2(getNode(start).getOtherSystemMustBeToOut2().toArray(new SystemsPos[0]));
        midNode.setOtherSystemMustBeToOut3(getNode(start).getOtherSystemMustBeToOut3().toArray(new SystemsPos[0]));
        midNode.addNeighbors(new SystemsPos[]{start, end});
    }

    public GBNode getMidNode() {
        return midNode;
    }

}
