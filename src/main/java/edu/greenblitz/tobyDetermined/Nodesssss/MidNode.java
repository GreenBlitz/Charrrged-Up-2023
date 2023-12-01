package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MidNode {
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

    public void setNewMidNode(RobotMap.TelescopicArm.PresetPositions start, RobotMap.TelescopicArm.PresetPositions end, double extenderPos, double anglePos) {
        if (start.equals(RobotMap.TelescopicArm.PresetPositions.MID_NODE))
            start = nodeArm.getNeighbors().get(0);
        nodeArm = new NodeArm(extenderPos, anglePos);
        SmartDashboard.putString("Current Node", start.toString());
        nodeArm.addNeighbors(new RobotMap.TelescopicArm.PresetPositions[]{start, end});
    }

    public NodeArm getNodeArm() {
        return nodeArm;
    }
}
