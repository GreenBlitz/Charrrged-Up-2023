package edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystemAlgorithm;

import edu.wpi.first.math.Pair;

import java.util.HashMap;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.*;

public class AStarEdges {

    private static void addNeighborsToAvailableEdges(SystemsState current, LinkedList<Edge> checkedEdges, LinkedList<Edge> availableEdges, Edge currentEdge, HashMap<Edge, Edge> parents) {
        for (SystemsState neighbor : getNode(current).getNeighbors()) {
            Edge currentToNeighbor = new Edge(current, neighbor, currentEdge.getSystem2State());
            if (isNotInEdgeList(checkedEdges, currentToNeighbor) && isNotInEdgeList(availableEdges, currentToNeighbor)) {
                availableEdges.add(currentToNeighbor);
                parents.put(availableEdges.get(availableEdges.size() - 1), currentEdge);
            }
        }
    }

    private static Edge getLowestCostEdge(LinkedList<Edge> availableEdges, HashMap<Edge, Pair<LinkedList<SystemsState>, Double>> pathMap) {
        int saveI = 0;
        int index = 0;
        double minFCost = Double.MAX_VALUE;

        while (index != availableEdges.size()) {
            boolean isEdgeUsable = isEdgeUsable(availableEdges.get(index));

            if (!isEdgeUsable) {
                availableEdges.remove(availableEdges.get(index));
            }
            else {
                double currentFCost = getEdgeCost(availableEdges.get(index), pathMap);
                if (currentFCost < minFCost) {
                    minFCost = currentFCost;
                    saveI = index;
                }
                index++;
            }
        }
        return availableEdges.get(saveI);
    }

    private static double getEdgeCost(Edge edge, HashMap<Edge, Pair<LinkedList<SystemsState>, Double>> pathMap) {
        if (edge.isValidForEdge(edge.getSystem2State())) {
            return edge.getTimeCost();
        }
        return getCostOfOtherSystemPathAndAddToMap(edge, pathMap) + edge.getTimeCost();
    }

    private static boolean isEdgeUsable(Edge edge) {
        if (edge.isValidForEdge(edge.getSystem2State())) {
            return true;
        }
        return canSystem2Move(edge) && isTherePossibleState(edge);
    }

    private static boolean canSystem2Move(Edge edge) {
        return getNode(edge.getSystem2State()).getSystem2MustBeToExitState().contains(edge.getStartState())
                || getNode(edge.getSystem2State()).getSystem2MustBeToExitState().isEmpty();
    }

    private static boolean isTherePossibleState(Edge edge) {
        LinkedList<SystemsState> otherSystemStates = getAllSystemsState();
        for (SystemsState otherSystemState : otherSystemStates) {
            if (edge.isValidForEdge(otherSystemState)) {
                return true;
            }
        }
        return false;
    }

    private static Double getCostOfOtherSystemPathAndAddToMap(Edge edge, HashMap<Edge, Pair<LinkedList<SystemsState>, Double>> pathMap) {
        SystemsState newStartState = edge.getSystem2State();
        LinkedList<SystemsState> list = edge.getReunion();
        double min = Double.MAX_VALUE;
        LinkedList<SystemsState> path = new LinkedList<>();
        for (SystemsState systemsState : list) {
            Pair<LinkedList<SystemsState>, Double> pair = getSystemPath(newStartState, systemsState, edge.getStartState());
            if (pair.getSecond() < min) {
                min = pair.getSecond();
                path = pair.getFirst();
            }
        }
        pathMap.put(edge, new Pair<>(path, min));
        edge.setSystem2State(path.get(path.size() - 1));
        return min;
    }

    private static Pair<LinkedList<SystemsState>, Double> getPathInListAndCost(Edge edge, HashMap<Edge, Edge> parents, HashMap<Edge, Pair<LinkedList<SystemsState>, Double>> pathMap) {
        LinkedList<SystemsState> pathList = new LinkedList<>();
        double totalCost = 0;
        Edge current = edge;
        while (current != null) {
            totalCost += current.getTimeCost();
            pathList.addFirst(current.getEndState());
            pathList.addFirst(current.getStartState());
            if (pathMap.containsKey(current)) {
                totalCost += pathMap.get(current).getSecond();
                addPathFromPathMap(current, pathMap, pathList);
            }
            current = parents.get(current);
        }
        return new Pair<>(pathList, totalCost);
    }

    private static void addPathFromPathMap(Edge current, HashMap<Edge, Pair<LinkedList<SystemsState>, Double>> pathMap, LinkedList<SystemsState> pathList) {
        for (int i = 0; i < pathMap.get(current).getFirst().size(); i++) {
            if (!pathList.get(i).equals(pathMap.get(current).getFirst().get(i)))
                pathList.add(i, pathMap.get(current).getFirst().get(i));
        }
    }

    private static Pair<LinkedList<SystemsState>, Double> getSystemPath(SystemsState start, SystemsState end, SystemsState system2State) {
        LinkedList<Edge> checkedEdges = new LinkedList<>();
        LinkedList<Edge> availableEdges = new LinkedList<>();
        HashMap<Edge, Edge> parents = new HashMap<>();
        HashMap<Edge, Pair<LinkedList<SystemsState>, Double>> pathMap = new HashMap<>();
        SystemsState current = start;
        Edge currentEdge;

        for (SystemsState neighbor : getNode(current).getNeighbors()) {
            availableEdges.add(new Edge(current, neighbor, system2State));
        }

        while (!availableEdges.isEmpty()) {

            currentEdge = getLowestCostEdge(availableEdges, pathMap);
            current = currentEdge.getEndState();
            availableEdges.remove(currentEdge);
            checkedEdges.add(currentEdge);

            if (current.equals(end)) {
                return getPathInListAndCost(currentEdge, parents, pathMap);
            }
            addNeighborsToAvailableEdges(current, checkedEdges, availableEdges, currentEdge, parents);
        }
        return null;
    }

    public static LinkedList<SystemsState> getFinalPath(SystemsState start, SystemsState end, SystemsState system2State) {
        return getSystemPath(start, end, system2State).getFirst();
    }

}

