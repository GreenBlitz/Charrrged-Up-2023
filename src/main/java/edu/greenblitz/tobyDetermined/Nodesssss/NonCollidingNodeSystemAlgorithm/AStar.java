package edu.greenblitz.tobyDetermined.Nodesssss.NonCollidingNodeSystemAlgorithm;


import edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemFunctions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemFunctions.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;


public class AStar {
    //TODO , CHECK IN FUNCTIONS
    public static void addToOpen(LinkedList<SystemsPos> nodesCanGoTo, SystemsPos current, LinkedList<SystemsPos> closed, Map<SystemsPos, SystemsPos> parents) {
        for (SystemsPos neighbor : getNode(current).getNeighbors()) {
            if (isNotInList(neighbor, closed) && isNotInList(neighbor, nodesCanGoTo)) {
                nodesCanGoTo.add(neighbor);
                parents.put(neighbor, current);
            }
        }
    }

    public static SystemsPos getLowestCostPosition(SystemsPos current, LinkedList<SystemsPos> open) {
        int saveI = 0;
        double cost = getCostByMap(current, open.get(0));
        for (int i = 1; i < open.size(); i++) {
            double currentCost = getCostByMap(current, open.get(i));
            if (currentCost < cost) {
                cost = currentCost;
                saveI = i;
            }
        }
        return open.get(saveI);
    }

    public static LinkedList<SystemsPos> returnPath(SystemsPos nodeArm, Map<SystemsPos, SystemsPos> parents) {
        LinkedList<SystemsPos> pathList = new LinkedList<>();
        SystemsPos current = nodeArm;
        while (current != null) {
            pathList.addFirst(current);
            current = parents.get(current);
        }
        return pathList;
    }

    public static LinkedList<SystemsPos> getPath(SystemsPos start, SystemsPos end) {
        LinkedList<SystemsPos> nodesCanGoTo = new LinkedList<>();
        LinkedList<SystemsPos> closed = new LinkedList<>();
        Map<SystemsPos, SystemsPos> parents = new HashMap<>();
        SystemsPos current = start;
        nodesCanGoTo.add(start);

        while (!nodesCanGoTo.isEmpty()) {
            current = getLowestCostPosition(current, nodesCanGoTo);
            nodesCanGoTo.remove(current);
            closed.add(current);

            if (current.equals(end)) {
                return returnPath(current, parents);
            }

            addToOpen(nodesCanGoTo, current, closed, parents);

        }
        return null;
    }

    //TODO DELETE OR FIND SOLUTION
    public static void main(String[] args) {
        LinkedList<SystemsPos> a = getPath(ARM_LOW, ARM_HIGH);
        printPath(a);
    }
}
