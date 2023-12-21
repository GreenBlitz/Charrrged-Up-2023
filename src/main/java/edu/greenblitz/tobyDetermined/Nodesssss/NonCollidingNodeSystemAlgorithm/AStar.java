package edu.greenblitz.tobyDetermined.Nodesssss.NonCollidingNodeSystemAlgorithm;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState;


public class AStar {

    private static SystemsState getLowestCostPosition(SystemsState current, LinkedList<SystemsState> open) {
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

    private static LinkedList<SystemsState> getPathInListAndCost(SystemsState nodePosition, Map<SystemsState, SystemsState> parents) {
        LinkedList<SystemsState> pathList = new LinkedList<>();
        SystemsState current = nodePosition;
        while (current != null) {
            pathList.addFirst(current);
            current = parents.get(current);
        }
        return pathList;
    }

    private static void addToAvailableNodes(LinkedList<SystemsState> availableNodes, SystemsState current, LinkedList<SystemsState> closed, Map<SystemsState, SystemsState> parents) {
        for (SystemsState neighbor : getNode(current).getNeighbors()) {
            if (isNotInList(neighbor, closed) && isNotInList(neighbor, availableNodes)) {
                availableNodes.add(neighbor);
                parents.put(neighbor, current);
            }
        }
    }

    public static LinkedList<SystemsState> getPath(SystemsState start, SystemsState end) {
        LinkedList<SystemsState> availableNodes = new LinkedList<>();
        LinkedList<SystemsState> closed = new LinkedList<>();
        Map<SystemsState, SystemsState> parents = new HashMap<>();
        SystemsState current = start;
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
        throw new RuntimeException("no working path for this states: "+start+", "+end);
    }

}
