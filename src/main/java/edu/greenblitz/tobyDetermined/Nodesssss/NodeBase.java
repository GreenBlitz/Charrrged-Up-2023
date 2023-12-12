package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.GriperNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.NodeArm;

import java.util.HashMap;
import java.util.LinkedList;


import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemConstants.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemConstants.SystemsPos.*;

public class NodeBase {

    protected final static HashMap<SystemsPos, GBNode> nodeMap = new HashMap<>();
    private static final LinkedList<SystemsPos> listSystemsPos = new LinkedList<>();


    static {

        nodeMap.put(ARM_LOWWW, new NodeArm(0, 0));
        nodeMap.put(ARM_GROUND, new NodeArm(1, 0));
        nodeMap.put(ARM_MID, new NodeArm(2, 0));
        nodeMap.put(ARM_HIGH, new NodeArm(3, 0));
        listSystemsPos.add(ARM_LOWWW);
        listSystemsPos.add(ARM_GROUND);
        listSystemsPos.add(ARM_MID);
        listSystemsPos.add(ARM_HIGH);

        nodeMap.get(ARM_LOWWW).addNeighbors(new SystemsPos[]{ARM_GROUND, ARM_MID, ARM_HIGH});
        nodeMap.get(ARM_GROUND).addNeighbors(new SystemsPos[]{ARM_LOWWW, ARM_MID, ARM_HIGH});
        nodeMap.get(ARM_MID).addNeighbors(new SystemsPos[]{ARM_GROUND, ARM_LOWWW, ARM_HIGH});
        nodeMap.get(ARM_HIGH).addNeighbors(new SystemsPos[]{ARM_GROUND, ARM_MID, ARM_LOWWW});

        nodeMap.put(GRIPER_OPEN, new GriperNode());
        nodeMap.put(GRIPER_CLOSE, new GriperNode());
        listSystemsPos.add(GRIPER_OPEN);
        listSystemsPos.add(GRIPER_CLOSE);

        nodeMap.get(GRIPER_OPEN).addNeighbors(new SystemsPos[]{GRIPER_CLOSE});
        nodeMap.get(GRIPER_CLOSE).addNeighbors(new SystemsPos[]{GRIPER_OPEN});

        nodeMap.get(ARM_LOWWW).setOtherSystemMustBeToEnter(new SystemsPos[]{GRIPER_OPEN});
        nodeMap.get(ARM_LOWWW).setOtherSystemMustBeToOut(new SystemsPos[]{GRIPER_OPEN});


    }

    public static GBNode getNode(SystemsPos specificNode) {
        if (specificNode.equals(MID_NODE))
            return MidNode.getInstance().getGBNode();
        return nodeMap.get(specificNode);
    }

    public static LinkedList<SystemsPos> getAllSystemsPositions() {
        return new LinkedList<>(listSystemsPos);
    }
}
