package edu.greenblitz.tobyDetermined.Nodesssss.NonCollidingNodeSystemAlgorithm;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPosition;


public class AStar {

    private static SystemsPosition getLowestCostPosition(SystemsPosition current, LinkedList<SystemsPosition> open) {
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

    private static boolean isNotInList(SystemsPosition nodePosition, LinkedList<SystemsPosition> list) {
        return !list.contains(nodePosition);
    }

    private static LinkedList<SystemsPosition> getPathInListAndCost(SystemsPosition nodePosition, Map<SystemsPosition, SystemsPosition> parents) {
        LinkedList<SystemsPosition> pathList = new LinkedList<>();
        SystemsPosition current = nodePosition;
        while (current != null) {
            pathList.addFirst(current);
            current = parents.get(current);
        }
        return pathList;
    }

    private static void addToAvailableNodes(LinkedList<SystemsPosition> availableNodes, SystemsPosition current, LinkedList<SystemsPosition> closed, Map<SystemsPosition, SystemsPosition> parents) {
        for (SystemsPosition neighbor : getNode(current).getNeighbors()) {
            if (isNotInList(neighbor, closed) && isNotInList(neighbor, availableNodes)) {
                availableNodes.add(neighbor);
                parents.put(neighbor, current);
            }
        }
    }

    public static LinkedList<SystemsPosition> getPath(SystemsPosition start, SystemsPosition end) {
        LinkedList<SystemsPosition> availableNodes = new LinkedList<>();
        LinkedList<SystemsPosition> closed = new LinkedList<>();
        Map<SystemsPosition, SystemsPosition> parents = new HashMap<>();
        SystemsPosition current = start;
        availableNodes.add(start);

        while (!availableNodes.isEmpty()) {
            current = getLowestCostPosition(current, availableNodes);
            availableNodes.remove(current);
            closed.add(current);

            if (current.equals(end)) {
                return getPathInListAndCost(current, parents);
            }

            addToAvailableNodes(availableNodes, current, closed, parents);

        }
        return null;
    }

}
