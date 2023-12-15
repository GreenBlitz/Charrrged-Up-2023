package edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystemAlgorithm;

import edu.wpi.first.math.Pair;
import java.util.HashMap;
import java.util.LinkedList;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemFunctions.*;

public class AStarVertex {
    //TODO , CHECK IN FUNCTIONS
    public static void printPathVertex(LinkedList<Vertex> pathList) {
        for (Vertex vertex : pathList) {
            System.out.print(vertex.getStartPos() + ", " + vertex.getEndPos() + ";;;;;");
        }
        System.out.println();
    }
    //TODO , CHECK IN FUNCTIONS
    private static boolean NotInVertexList(LinkedList<Vertex> verList, Vertex ver) {
        for (Vertex vertex : verList) {
            if (vertex.getStartPos().equals(ver.getStartPos()) && vertex.getEndPos().equals(ver.getEndPos()))
                return false;
        }
        return true;
    }
    //TODO , CHECK IN FUNCTIONS
    public static void addNeighborsToOpen(SystemsPos current, LinkedList<Vertex> closedVer, LinkedList<Vertex> openVer, Vertex currentVer, HashMap<Vertex, Vertex> parents) {
        for (SystemsPos neighbor : getNode(current).getNeighbors()) {
            if (NotInVertexList(closedVer, new Vertex(current, neighbor, currentVer.getSystem2Pos()))
                    && NotInVertexList(openVer, new Vertex(current, neighbor, currentVer.getSystem2Pos())
            )) {
                openVer.add(new Vertex(current, neighbor, currentVer.getSystem2Pos()));
                parents.put(openVer.get(openVer.size() - 1), currentVer);
            }
        }
    }

    public static Vertex getLowestCostVertex(LinkedList<Vertex> open, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
        int indexOfLowestCost = 0;
        int indexInList = 0;
        double minCost = Double.MAX_VALUE;

        while (indexInList != open.size()) {
            Pair<Boolean, Double> vertexUsableAndCost = IsVertexUsableAndCost(open.get(indexInList), pathMap);

            if (!vertexUsableAndCost.getFirst())
                open.remove(open.get(indexInList));

            else {
                double currentFCost = vertexUsableAndCost.getSecond() + open.get(indexInList).getTimeCost();
                if (currentFCost < minCost) {
                    minCost = currentFCost;
                    indexOfLowestCost = indexInList;
                }
                indexInList++;
            }
        }

        return open.get(indexOfLowestCost);
    }

    public static Pair<Boolean, Double> IsVertexUsableAndCost(Vertex vertex, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
        if (vertex.isPosFineForVertexSystem2(vertex.getSystem2Pos())) {
            return new Pair<>(true, 0.0);
        }
        if (isSystem2CanMove(vertex) && isTherePossiblePosition(vertex)) {
            return new Pair<>(true, getCostOfOtherSystemPathAndAddToMap(vertex, pathMap));
        }
        return new Pair<>(false, 0.0);
    }

    public static Boolean isSystem2CanMove(Vertex vertex) {
        return getNode(vertex.getSystem2Pos()).getOtherSystemMustBeToOut2().contains(vertex.getStartPos());
    }

    public static Boolean isTherePossiblePosition(Vertex vertex){
        LinkedList<SystemsPos> otherSystemPositions = getAllSystemsPositions();
        for (SystemsPos otherSystemPosition : otherSystemPositions) {
            if (vertex.isPosFineForVertexSystem2(otherSystemPosition)) {
                return true;
            }
        }
        return false;
    }

    public static Double getCostOfOtherSystemPathAndAddToMap(Vertex vertex, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
        SystemsPos pos = vertex.getSystem2Pos();
        LinkedList<SystemsPos> list = vertex.mergeCommonNodes2();
        double min = Double.MAX_VALUE;
        LinkedList<SystemsPos> path = new LinkedList<>();
        for (SystemsPos systemsPos : list) {
            Pair<LinkedList<SystemsPos>, Double> pair = findPath(pos, systemsPos, vertex.getStartPos());
            if (pair.getSecond() < min) {
                min = pair.getSecond();
                path = pair.getFirst();
            }
        }
        pathMap.put(vertex, new Pair<>(path, min));
        vertex.setSystem2Pos(path.get(path.size() - 1));
        return min;
    }

    public static Pair<LinkedList<SystemsPos>, Double> returnPath(Vertex vertex, HashMap<Vertex, Vertex> parents, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
        LinkedList<SystemsPos> pathList = new LinkedList<>();
        double totalCost = 0;
        Vertex current = vertex;
        while (current != null) {
            totalCost += current.getTimeCost();
            pathList.addFirst(current.getEndPos());
            pathList.addFirst(current.getStartPos());
            if (pathMap.containsKey(current)) {
                totalCost += pathMap.get(current).getSecond();
                addPathFromPathMap(current, pathMap, pathList);
            }
            current = parents.get(current);
        }
        return new Pair<>(pathList, totalCost);
    }

    public static void addPathFromPathMap(Vertex current,  HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap, LinkedList<SystemsPos> pathList){
        for (int i = 0; i < pathMap.get(current).getFirst().size(); i++) {
            if (!pathList.get(i).equals(pathMap.get(current).getFirst().get(i)))
                pathList.add(i, pathMap.get(current).getFirst().get(i));
        }
    }

    public static Pair<LinkedList<SystemsPos>, Double> findPath(SystemsPos start, SystemsPos end, SystemsPos secondSystemPos) {
        LinkedList<Vertex> closedVer = new LinkedList<>();
        LinkedList<Vertex> openVer = new LinkedList<>();
        HashMap<Vertex, Vertex> parents = new HashMap<>();
        HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap = new HashMap<>();
        SystemsPos current = start;
        Vertex currentVer;

        for (SystemsPos neighbor : getNode(current).getNeighbors()) {
                openVer.add(new Vertex(current, neighbor, secondSystemPos));
        }

        while (!openVer.isEmpty()) {
            currentVer = getLowestCostVertex(openVer, pathMap);
            current = currentVer.getEndPos();
            openVer.remove(currentVer);
            closedVer.add(currentVer);

            if (current.equals(end)) {
                return returnPath(currentVer, parents, pathMap);
            }
            addNeighborsToOpen(current, closedVer, openVer, currentVer, parents);
        }

        return null;
    }

    public static LinkedList<SystemsPos> returnFinalPath(SystemsPos start, SystemsPos end, SystemsPos system2Pos) {
        return findPath(start, end, system2Pos).getFirst();
    }

    //TODO DELETE OR FIND SOLUTION
    public static void main(String[] args) {
        LinkedList<SystemsPos> a = returnFinalPath(SystemsPos.GRIPER_OPEN, SystemsPos.GRIPER_CLOSE, SystemsPos.ARM_LOW);
        printPath(a);
    }
}

