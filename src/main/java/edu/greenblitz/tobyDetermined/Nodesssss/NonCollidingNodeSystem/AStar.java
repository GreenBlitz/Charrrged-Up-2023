package edu.greenblitz.tobyDetermined.Nodesssss.NonCollidingNodeSystem;

import edu.greenblitz.tobyDetermined.Nodesssss.GBNode;
import edu.greenblitz.tobyDetermined.Nodesssss.MidNode;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.GBMath;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos;
import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem;

public class AStar {

    public static double getDistanceToStartPlusEnd(SystemsPos current, SystemsPos target) {
        return NodeSystem.getCostByMap(current, target);
    }


    public static SystemsPos getLowestFcost(SystemsPos current, LinkedList<SystemsPos> open, GBNode start, GBNode end) {
        int saveI = 0;
        double fCost = getDistanceToStartPlusEnd(current, open.get(0));
        for (int i = 1; i < open.size(); i++) {
            double currentFCost = getDistanceToStartPlusEnd(current, open.get(i));
            if (currentFCost < fCost) {
                fCost = currentFCost;
                saveI = i;
            }
        }
        return open.get(saveI);
    }


    public static boolean isInList(SystemsPos nodeArmPos, LinkedList<SystemsPos> list) {
        return list.contains(nodeArmPos);
    }

    public static LinkedList<SystemsPos> returnPath(SystemsPos nodeArm, Map<SystemsPos, SystemsPos> parents) {
        LinkedList<SystemsPos> pathList = new LinkedList<>();
        SystemsPos current = nodeArm;
        while (current != null) {
            pathList.addFirst(current);
            current = parents.get(current);
        }
        printPath(pathList);
        return pathList;
    }

    public static void printPath(LinkedList<SystemsPos> pathList) {
        for (int i = 0; i <= pathList.size() - 1; i++) {
            System.out.print(pathList.get(i) + ", ");
        }
        System.out.println();
    }

    public static void addToOpen(LinkedList<SystemsPos> nodesCanGoTo, SystemsPos current, LinkedList<SystemsPos> closed, Map<SystemsPos, SystemsPos> parents) {
        for (SystemsPos neighbor : NodeBase.getNode(current).getNeighbors()) {
            if (!isInList(neighbor, closed) && !isInList(neighbor, nodesCanGoTo)) {
                nodesCanGoTo.add(neighbor);
                parents.put(neighbor, current);
            }
        }
    }

    public static LinkedList<SystemsPos> getPath(SystemsPos start, SystemsPos end) {
        LinkedList<SystemsPos> nodesCanGoTo = new LinkedList<>();
        LinkedList<SystemsPos> closed = new LinkedList<>();
        Map<SystemsPos, SystemsPos> parents = new HashMap<>();
        SystemsPos current = start;
        nodesCanGoTo.add(start);
        while (!nodesCanGoTo.isEmpty()) {
            current = getLowestFcost(current, nodesCanGoTo, NodeBase.getNode(start), NodeBase.getNode(end));
            nodesCanGoTo.remove(current);
            closed.add(current);

            if (current.equals(end)) {
                return returnPath(current, parents);
            }

            addToOpen(nodesCanGoTo, current, closed, parents);

        }
        return null;
    }

    public static void main(String[] args) {
        LinkedList<SystemsPos> a = getPath(ARM_LOWWW, ARM_HIGH);
    }
}
