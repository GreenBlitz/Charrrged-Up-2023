package edu.greenblitz.utils.NodeSystemUtils;

import edu.greenblitz.utils.NodeSystemUtils.CollidingNodeSystemAlgorithm.Edge;

import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.SystemsState;
import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.CreateNodes.*;



public class NodeSystemFunctions {



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
