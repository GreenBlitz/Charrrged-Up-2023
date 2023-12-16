package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.TheNodes.GriperNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.MidNodes.System1MidNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.TheNodes.NodeArm;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.wpi.first.math.Pair;

import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SetCosts.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPosition.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPosition;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.CreateNodes.*;



public class NodeSystemUtils {

    public static GBNode getNodeBySystemName(SystemsPosition start) {
        if (start.toString().contains(systemName1))
            return new NodeArm(Extender.getInstance().getLength(), Elbow.getInstance().getAngleRadians());
        return new GriperNode();
    }
    public static double getCostByMap(SystemsPosition a, SystemsPosition b) {
        for (Pair<String, Double> stringDoublePair : costList) {
            if (stringDoublePair.getFirst().contains(a.toString()) && stringDoublePair.getFirst().contains(b.toString()))
                return stringDoublePair.getSecond();
        }
        return 0;
    }
    public static GBNode getNode(SystemsPosition specificNode) {
        if (specificNode.equals(MID_NODE))
            return System1MidNode.getInstance().getGBNode();
        return nodeMap.get(specificNode);
    }
    public static <T> void printPath(LinkedList<T> pathList) {
        for (T t : pathList) {
            System.out.print(t + ", ");
        }
        System.out.println();
    }
    public static LinkedList<SystemsPosition> getAllSystemsPositions() {
        return new LinkedList<>(listSystemsPosition);
    }

}
