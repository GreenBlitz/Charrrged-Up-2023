package edu.greenblitz.tobyDetermined.RobotNodeSystem;

import edu.greenblitz.utils.NodeSystemUtils.TheSystemsNodes.GBNode;
import edu.greenblitz.tobyDetermined.RobotNodeSystem.TheNodes.GriperNode;
import edu.greenblitz.tobyDetermined.RobotNodeSystem.TheNodes.NodeArm;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.wpi.first.math.Pair;

import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.Constants.systemName1;
import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.CreateCurrents.system1MidNode;
import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.CreateCurrents.system2MidNode;
import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.CreateNodes.nodeMap;
import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.SetCosts.costList;
import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.SystemsState.MID_NODE_1;
import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.SystemsState.MID_NODE_2;
import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.SystemsState;

public class NodeSystemDependentFunctions {

    public static GBNode getNodeBySystemName(SystemsState start) {
        if (start.systemName.equals(systemName1))
            return new NodeArm(Extender.getInstance().getLength(), Elbow.getInstance().getAngleRadians());
        return new GriperNode();
    }

    public static double getCostByMap(SystemsState systemsState1, SystemsState systemsState2) {
        if (systemsState1.equals(MID_NODE_1) || systemsState1.equals(MID_NODE_2) ||systemsState2.equals(MID_NODE_1) ||systemsState2.equals(MID_NODE_2))
            return 0;
        for (Pair<String, Double> stringDoublePair : costList) {
            if (stringDoublePair.getFirst().contains(systemsState2.toString()) && stringDoublePair.getFirst().contains(systemsState1.toString()))
                return stringDoublePair.getSecond();
        }
        throw new RuntimeException("No cost found in map for this states: "+systemsState1+", "+systemsState2);
    }

    public static GBNode getNode(SystemsState specificNode) {
        if (specificNode.equals(MID_NODE_1))
            return system1MidNode.getMidNode();
        if (specificNode.equals(MID_NODE_2))
            return system2MidNode.getMidNode();
        return nodeMap.get(specificNode);
    }


}
