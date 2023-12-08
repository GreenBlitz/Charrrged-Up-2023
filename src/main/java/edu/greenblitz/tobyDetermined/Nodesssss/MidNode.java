package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.GriperNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.NodeArm;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;

import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.*;

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

    public void setNewMidNode(RobotMap.NodeSystem.SystemsPos start, RobotMap.NodeSystem.SystemsPos end) {
        if (start.equals(RobotMap.NodeSystem.SystemsPos.MID_NODE))
            start = gbNode.getNeighbors().get(0);
        if (start.toString().contains(systemName1))
            gbNode = new NodeArm(Extender.getInstance().getLength(), Elbow.getInstance().getAngleRadians());
        else
            gbNode = new GriperNode();
        gbNode.addNeighbors(new RobotMap.NodeSystem.SystemsPos[]{start, end});
    }

    public GBNode getGBNode() {
        return gbNode;
    }
}
