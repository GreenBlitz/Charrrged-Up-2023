package edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystemAlgorithm;

import edu.wpi.first.math.Pair;

import java.util.HashMap;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.*;

public class AStarVertex {

    private static boolean notInVertexList(LinkedList<Vertex> vertexList, Vertex targetVertex) {
        for (Vertex vertex : vertexList) {
            if (vertex.getStartState().equals(targetVertex.getStartState()) && vertex.getEndState().equals(targetVertex.getEndState()))
                return false;
        }
        return true;
    }

    private static void addNeighborsToAvailableVertexes(SystemsState current, LinkedList<Vertex> checkedVertexes, LinkedList<Vertex> availableVertexes, Vertex currentVertex, HashMap<Vertex, Vertex> parents) {
        for (SystemsState neighbor : getNode(current).getNeighbors()) {
            Vertex currentToNeighbor = new Vertex(current, neighbor, currentVertex.getSystem2State());
            if (notInVertexList(checkedVertexes, currentToNeighbor) && notInVertexList(availableVertexes, currentToNeighbor)) {
                availableVertexes.add(currentToNeighbor);
                parents.put(availableVertexes.get(availableVertexes.size() - 1), currentVertex);
            }
        }
    }

    private static Vertex getLowestCostVertex(LinkedList<Vertex> availableVertexes, HashMap<Vertex, Pair<LinkedList<SystemsState>, Double>> pathMap) {
        int saveI = 0;
        int index = 0;
        double minFCost = Double.MAX_VALUE;

        while (index != availableVertexes.size()) {
            Pair<Boolean, Double> vertexUsableAndCost = isVertexUsableAndCost(availableVertexes.get(index), pathMap);

            if (!vertexUsableAndCost.getFirst()) {
                availableVertexes.remove(availableVertexes.get(index));
            } else {
                double currentFCost = vertexUsableAndCost.getSecond();
                currentFCost += availableVertexes.get(index).getTimeCost();
                if (currentFCost < minFCost) {
                    minFCost = currentFCost;
                    saveI = index;
                }

                index++;

            }
        }
        return availableVertexes.get(saveI);
    }

    private static Pair<Boolean, Double> isVertexUsableAndCost(Vertex vertex, HashMap<Vertex, Pair<LinkedList<SystemsState>, Double>> pathMap) {
        if (vertex.isValidForVertex(vertex.getSystem2State())) {
            return new Pair<>(true, 0.0);
        }

        if (canSystem2Move(vertex) && isTherePossibleState(vertex)) {
            return new Pair<>(true, getCostOfOtherSystemPathAndAddToMap(vertex, pathMap));
        }

        return new Pair<>(false, 0.0);
    }

    private static boolean canSystem2Move(Vertex vertex) {
        return getNode(vertex.getSystem2State()).getSystem2MustBeToExitState().contains(vertex.getStartState())
                || getNode(vertex.getSystem2State()).getSystem2MustBeToExitState().isEmpty();
    }

    private static boolean isTherePossibleState(Vertex vertex) {
        LinkedList<SystemsState> otherSystemStates = getAllSystemsState();
        for (SystemsState otherSystemState : otherSystemStates) {
            if (vertex.isValidForVertex(otherSystemState)) {
                return true;
            }
        }
        return false;
    }

    private static Double getCostOfOtherSystemPathAndAddToMap(Vertex vertex, HashMap<Vertex, Pair<LinkedList<SystemsState>, Double>> pathMap) {
        SystemsState newStartState = vertex.getSystem2State();
        LinkedList<SystemsState> list = vertex.getReunion();
        double min = Double.MAX_VALUE;
        LinkedList<SystemsState> path = new LinkedList<>();
        for (SystemsState systemsState : list) {
            Pair<LinkedList<SystemsState>, Double> pair = getSystemPath(newStartState, systemsState, vertex.getStartState());
            if (pair.getSecond() < min) {
                min = pair.getSecond();
                path = pair.getFirst();
            }
        }
        pathMap.put(vertex, new Pair<>(path, min));
        vertex.setSystem2State(path.get(path.size() - 1));
        return min;
    }

    private static Pair<LinkedList<SystemsState>, Double> getPathInListAndCost(Vertex vertex, HashMap<Vertex, Vertex> parents, HashMap<Vertex, Pair<LinkedList<SystemsState>, Double>> pathMap) {
        LinkedList<SystemsState> pathList = new LinkedList<>();
        double totalCost = 0;
        Vertex current = vertex;
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

    private static void addPathFromPathMap(Vertex current, HashMap<Vertex, Pair<LinkedList<SystemsState>, Double>> pathMap, LinkedList<SystemsState> pathList) {
        for (int i = 0; i < pathMap.get(current).getFirst().size(); i++) {
            if (!pathList.get(i).equals(pathMap.get(current).getFirst().get(i)))
                pathList.add(i, pathMap.get(current).getFirst().get(i));
        }
    }

    private static Pair<LinkedList<SystemsState>, Double> getSystemPath(SystemsState start, SystemsState end, SystemsState system2State) {
        LinkedList<Vertex> checkedVertexes = new LinkedList<>();
        LinkedList<Vertex> availableVertexes = new LinkedList<>();
        HashMap<Vertex, Vertex> parents = new HashMap<>();
        HashMap<Vertex, Pair<LinkedList<SystemsState>, Double>> pathMap = new HashMap<>();
        SystemsState current = start;
        Vertex currentVertex;

        for (SystemsState neighbor : getNode(current).getNeighbors()) {
            availableVertexes.add(new Vertex(current, neighbor, system2State));
        }

        while (!availableVertexes.isEmpty()) {

            currentVertex = getLowestCostVertex(availableVertexes, pathMap);
            current = currentVertex.getEndState();
            availableVertexes.remove(currentVertex);
            checkedVertexes.add(currentVertex);

            if (current.equals(end)) {
                return getPathInListAndCost(currentVertex, parents, pathMap);
            }
            addNeighborsToAvailableVertexes(current, checkedVertexes, availableVertexes, currentVertex, parents);
        }
        return null;
    }

    public static LinkedList<SystemsState> getFinalPath(SystemsState start, SystemsState end, SystemsState system2State) {
        return getSystemPath(start, end, system2State).getFirst();
    }

}

