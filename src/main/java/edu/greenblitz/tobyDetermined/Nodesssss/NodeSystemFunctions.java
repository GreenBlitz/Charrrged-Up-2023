package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.ClimbingNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.GriperNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.NodeArm;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.wpi.first.math.Pair;

import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SetCosts.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.CreateNodes.*;


public class NodeSystemFunctions {

    public static GBNode getNodeBySystemName(SystemsPos start) {
        if (start.toString().contains(systemName1))
            return new NodeArm(Extender.getInstance().getLength(), Elbow.getInstance().getAngleRadians());
        else if (start.toString().contains(systemName2))
            return new GriperNode();
        return new ClimbingNode();
    }

    public static double getCostByMap(SystemsPos a, SystemsPos b) {
        for (Pair<String, Double> stringDoublePair : costList) {
            if (stringDoublePair.getFirst().contains(a.toString()) && stringDoublePair.getFirst().contains(b.toString()))
                return stringDoublePair.getSecond();
        }
        return 0;
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
