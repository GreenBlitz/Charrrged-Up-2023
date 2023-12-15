package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.MidNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.GBNode;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.system3StartingNode;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.getNode;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.getNodeBySystemName;

public class MidNodeSystem3 {
    private GBNode midNode;
    private static MidNodeSystem3 instance;

    private MidNodeSystem3() {
        setNewMidNode(system3StartingNode, system3StartingNode);
    }

    public static MidNodeSystem3 getInstance() {
        if (instance == null)
            instance = new MidNodeSystem3();
        return instance;
    }

    public void setNewMidNode(NodeBase.SystemsPos start, NodeBase.SystemsPos end) {
        if (start.equals(NodeBase.SystemsPos.MID_NODE))
            start = midNode.getNeighbors().get(0);

        midNode = getNodeBySystemName(start);

        midNode.setOtherSystemMustBeToOut2(getNode(start).getOtherSystemMustBeToOut2().toArray(new NodeBase.SystemsPos[0]));
        midNode.setOtherSystemMustBeToOut3(getNode(start).getOtherSystemMustBeToOut3().toArray(new NodeBase.SystemsPos[0]));
        midNode.addNeighbors(new NodeBase.SystemsPos[]{start, end});
    }

    public GBNode getMidNode() {
        return midNode;
    }
}
