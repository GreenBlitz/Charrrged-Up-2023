package edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystemAlgorithm;

import edu.wpi.first.math.Pair;

import java.util.HashMap;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPosition;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.*;

public class AStarVertex {

    private static boolean NotInVertexList(LinkedList<Vertex> verList, Vertex ver) {
        for (Vertex vertex : verList) {
            if (vertex.getStartPosition().equals(ver.getStartPosition()) && vertex.getEndPosition().equals(ver.getEndPosition()))
                return false;
        }
        return true;
    }

    private static void addNeighborsToAvailableNodes(SystemsPosition current, LinkedList<Vertex> closedVer, LinkedList<Vertex> availableNodes, Vertex currentVer, HashMap<Vertex, Vertex> parents) {
        for (SystemsPosition neighbor : getNode(current).getNeighbors()) {
            Vertex currentToNeighbor = new Vertex(current, neighbor, currentVer.getSystem2Position());
            if (NotInVertexList(closedVer, currentToNeighbor) && NotInVertexList(availableNodes, currentToNeighbor)) {
                availableNodes.add(currentToNeighbor);
                parents.put(availableNodes.get(availableNodes.size() - 1), currentVer);
            }
        }
    }

    private static Vertex getLowestCostVertex(LinkedList<Vertex> open, HashMap<Vertex, Pair<LinkedList<SystemsPosition>, Double>> pathMap) {
        int saveI = 0;
        int index = 0;
        double minFCost = Double.MAX_VALUE;

        while (index != open.size()) {
            Pair<Boolean, Double> vertexUsableAndCost = IsVertexUsableAndCost(open.get(index), pathMap);

            if (!vertexUsableAndCost.getFirst()) {
                open.remove(open.get(index));
            } else {
                double currentFCost = vertexUsableAndCost.getSecond();
                currentFCost += open.get(index).getTimeCost();
                if (currentFCost < minFCost) {
                    minFCost = currentFCost;
                    saveI = index;
                }

                index++;

            }
        }
        return open.get(saveI);
    }

    private static Pair<Boolean, Double> IsVertexUsableAndCost(Vertex vertex, HashMap<Vertex, Pair<LinkedList<SystemsPosition>, Double>> pathMap) {
        if (vertex.isPositionFineForVertex(vertex.getSystem2Position())) {
            return new Pair<>(true, 0.0);
        }

        if (canSystem2Move(vertex) && isTherePossiblePosition(vertex)) {
            return new Pair<>(true, getCostOfOtherSystemPathAndAddToMap(vertex, pathMap));
        }

        return new Pair<>(false, 0.0);
    }

    private static boolean canSystem2Move(Vertex vertex) {
        return getNode(vertex.getSystem2Position()).getSystem2MustBeToOut().contains(vertex.getStartPosition())
                || getNode(vertex.getSystem2Position()).getSystem2MustBeToOut().isEmpty();
    }

    private static boolean isTherePossiblePosition(Vertex vertex) {
        LinkedList<SystemsPosition> otherSystemPositions = getAllSystemsPositions();
        for (SystemsPosition otherSystemPosition : otherSystemPositions) {
            if (vertex.isPositionFineForVertex(otherSystemPosition)) {
                return true;
            }
        }
        return false;
    }

    private static Double getCostOfOtherSystemPathAndAddToMap(Vertex vertex, HashMap<Vertex, Pair<LinkedList<SystemsPosition>, Double>> pathMap) {
        SystemsPosition newStartPosition = vertex.getSystem2Position();
        LinkedList<SystemsPosition> list = vertex.getReunion();
        double min = Double.MAX_VALUE;
        LinkedList<SystemsPosition> path = new LinkedList<>();
        for (SystemsPosition systemsPosition : list) {
            Pair<LinkedList<SystemsPosition>, Double> pair = getSystemPath(newStartPosition, systemsPosition, vertex.getStartPosition());
            if (pair.getSecond() < min) {
                min = pair.getSecond();
                path = pair.getFirst();
            }
        }
        pathMap.put(vertex, new Pair<>(path, min));
        vertex.setSystem2Position(path.get(path.size() - 1));
        return min;
    }

    private static Pair<LinkedList<SystemsPosition>, Double> getPathInListAndCost(Vertex vertex, HashMap<Vertex, Vertex> parents, HashMap<Vertex, Pair<LinkedList<SystemsPosition>, Double>> pathMap) {
        LinkedList<SystemsPosition> pathList = new LinkedList<>();
        double totalCost = 0;
        Vertex current = vertex;
        while (current != null) {
            totalCost += current.getTimeCost();
            pathList.addFirst(current.getEndPosition());
            pathList.addFirst(current.getStartPosition());
            if (pathMap.containsKey(current)) {
                totalCost += pathMap.get(current).getSecond();
                addPathFromPathMap(current, pathMap, pathList);
            }
            current = parents.get(current);
        }
        return new Pair<>(pathList, totalCost);
    }

    private static void addPathFromPathMap(Vertex current, HashMap<Vertex, Pair<LinkedList<SystemsPosition>, Double>> pathMap, LinkedList<SystemsPosition> pathList) {
        for (int i = 0; i < pathMap.get(current).getFirst().size(); i++) {
            if (!pathList.get(i).equals(pathMap.get(current).getFirst().get(i)))
                pathList.add(i, pathMap.get(current).getFirst().get(i));
        }
    }

    private static Pair<LinkedList<SystemsPosition>, Double> getSystemPath(SystemsPosition start, SystemsPosition end, SystemsPosition system2Position) {
        LinkedList<Vertex> closedVer = new LinkedList<>();
        LinkedList<Vertex> availableVertexes = new LinkedList<>();
        HashMap<Vertex, Vertex> parents = new HashMap<>();
        HashMap<Vertex, Pair<LinkedList<SystemsPosition>, Double>> pathMap = new HashMap<>();
        SystemsPosition current = start;
        Vertex currentVer;

        for (SystemsPosition neighbor : getNode(current).getNeighbors()) {
            availableVertexes.add(new Vertex(current, neighbor, system2Position));
        }

        while (!availableVertexes.isEmpty()) {

            currentVer = getLowestCostVertex(availableVertexes, pathMap);
            current = currentVer.getEndPosition();
            availableVertexes.remove(currentVer);
            closedVer.add(currentVer);

            if (current.equals(end)) {
                return getPathInListAndCost(currentVer, parents, pathMap);
            }
            addNeighborsToAvailableNodes(current, closedVer, availableVertexes, currentVer, parents);
        }
        return null;
    }

    public static LinkedList<SystemsPosition> getFinalPath(SystemsPosition start, SystemsPosition end, SystemsPosition system2Position) {
        return getSystemPath(start, end, system2Position).getFirst();
    }

}

