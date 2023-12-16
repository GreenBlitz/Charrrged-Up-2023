package edu.greenblitz.tobyDetermined.Nodesssss.NonCollidingNodeSystemAlgorithm;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPosition;


public class AStar {

    private static SystemsPosition getLowestCost(SystemsPosition current, LinkedList<SystemsPosition> open) {
        int saveI = 0;
        double fCost = getCostByMap(current, open.get(0));
        for (int i = 1; i < open.size(); i++) {
            double currentFCost = getCostByMap(current, open.get(i));
            if (currentFCost < fCost) {
                fCost = currentFCost;
                saveI = i;
            }
        }
        return open.get(saveI);
    }

    private static boolean isNotInList(SystemsPosition nodeArmPosition, LinkedList<SystemsPosition> list) {
        return !list.contains(nodeArmPosition);
    }

    private static LinkedList<SystemsPosition> gatherPathInList(SystemsPosition nodeArm, Map<SystemsPosition, SystemsPosition> parents) {
        LinkedList<SystemsPosition> pathList = new LinkedList<>();
        SystemsPosition current = nodeArm;
        while (current != null) {
            pathList.addFirst(current);
            current = parents.get(current);
        }
        printPath(pathList);
        return pathList;
    }

    private static void addToOpen(LinkedList<SystemsPosition> nodesCanGoTo, SystemsPosition current, LinkedList<SystemsPosition> closed, Map<SystemsPosition, SystemsPosition> parents) {
        for (SystemsPosition neighbor : getNode(current).getNeighbors()) {
            if (isNotInList(neighbor, closed) && isNotInList(neighbor, nodesCanGoTo)) {
                nodesCanGoTo.add(neighbor);
                parents.put(neighbor, current);
            }
        }
    }

    public static LinkedList<SystemsPosition> getPath(SystemsPosition start, SystemsPosition end) {
        LinkedList<SystemsPosition> nodesCanGoTo = new LinkedList<>();
        LinkedList<SystemsPosition> closed = new LinkedList<>();
        Map<SystemsPosition, SystemsPosition> parents = new HashMap<>();
        SystemsPosition current = start;
        nodesCanGoTo.add(start);

        while (!nodesCanGoTo.isEmpty()) {
            current = getLowestCost(current, nodesCanGoTo);
            nodesCanGoTo.remove(current);
            closed.add(current);

            if (current.equals(end)) {
                return gatherPathInList(current, parents);
            }

            addToOpen(nodesCanGoTo, current, closed, parents);

        }
        return null;
    }

}
