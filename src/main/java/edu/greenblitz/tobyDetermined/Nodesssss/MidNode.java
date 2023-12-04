package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.ArmCommand;

public class MidNode{
    private NodeArm nodeArm;
    private static MidNode instance;
    private MidNode() {
        nodeArm = null;
    }
    public static MidNode getInstance() {
        if (instance == null)
            instance = new MidNode();
        return instance;
    }
    public void setNewMidNode(RobotMap.NodeSystem.SystemsPos start, RobotMap.NodeSystem.SystemsPos end, double extenderPos, double anglePos) {
        if (start.equals(RobotMap.NodeSystem.SystemsPos.MID_NODE))
            start = nodeArm.getNeighbors().get(0);
        nodeArm = new NodeArm(extenderPos,anglePos, new ArmCommand());
        nodeArm.addNeighbors(new RobotMap.NodeSystem.SystemsPos[] {start,end});
    }

    public NodeArm getNodeArm() {
        return nodeArm;
    }
}
