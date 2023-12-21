package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystemAlgorithm.Edge;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.MidNodes.System2MidNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.TheNodes.GriperNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.MidNodes.System1MidNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.TheNodes.NodeArm;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.wpi.first.math.Pair;

import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SetCosts.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.CreateNodes.*;



public class NodeSystemUtils {

    public static GBNode getNodeBySystemName(SystemsState start) {
        if (start.toString().contains(systemName1))
            return new NodeArm(Extender.getInstance().getLength(), Elbow.getInstance().getAngleRadians());
        return new GriperNode();
    }

    public static double getCostByMap(SystemsState a, SystemsState b) {
        for (Pair<String, Double> stringDoublePair : costList) {
            if (stringDoublePair.getFirst().contains(a.toString()) && stringDoublePair.getFirst().contains(b.toString()))
                return stringDoublePair.getSecond();
        }
        return -1;
    }

    public static GBNode getNode(SystemsState specificNode) {
        if (specificNode.equals(MID_NODE_1))
            return System1MidNode.getMidNode();
        if (specificNode.equals(MID_NODE_2))
            return System2MidNode.getMidNode();
        return nodeMap.get(specificNode);
    }

    public static boolean isNotInEdgeList(LinkedList<Edge> edgeList, Edge targetEdge) {
        for (Edge edge : edgeList) {
            if (edge.getStartState().equals(targetEdge.getStartState()) && edge.getEndState().equals(targetEdge.getEndState()))
                return false;
        }
        return true;
    }

    public static <T> void printPath(LinkedList<T> pathList) {
        for (T object : pathList) {
            System.out.print(object + ", ");
        }
        System.out.println();
    }

    public static <T> boolean isNotInList(T object, LinkedList<T> list) {
        return !list.contains(object);
    }

    public static LinkedList<SystemsState> getAllSystemsState() {
        return new LinkedList<>(listSystemsStates);
    }

}
