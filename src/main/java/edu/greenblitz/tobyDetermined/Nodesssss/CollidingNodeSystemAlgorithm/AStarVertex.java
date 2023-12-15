package edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystemAlgorithm;

import edu.greenblitz.tobyDetermined.Nodesssss.GBNode;
import edu.greenblitz.tobyDetermined.Nodesssss.Vertexes.Vertex;
import edu.wpi.first.math.Pair;
import java.util.HashMap;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.systemName1;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos.ARM_ZG;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.Vertexes.VertexFunctions.getAllSystemPositions;

public class AStarVertex {
    //TODO , CHECK IN FUNCTIONS
    private static void printPathVertex(LinkedList<Vertex> pathList) {
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
    private static void addNeighborsToOpen(SystemsPos current, LinkedList<Vertex> closedVer, LinkedList<Vertex> openVer, Vertex currentVer, HashMap<Vertex, Vertex> parents, SystemsPos secondSystem) {
        for (SystemsPos neighbor : getNode(current).getNeighbors()) {
            if (NotInVertexList(closedVer, new Vertex(current, neighbor, currentVer.smartGetSystemPos(secondSystem)))
                    && NotInVertexList(openVer, new Vertex(current, neighbor, currentVer.smartGetSystemPos(secondSystem))
            )) {
                openVer.add(new Vertex(current, neighbor, currentVer.smartGetSystemPos(secondSystem)));
                parents.put(openVer.get(openVer.size() - 1), currentVer);
            }
        }
    }

    private static Vertex getLowestCostVertex(LinkedList<Vertex> open, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap, SystemsPos secondSystem) {
        int indexOfLowestCost = 0;
        int indexInList = 0;
        double minCost = Double.MAX_VALUE;

        while (indexInList != open.size()) {
            //System.out.println(open.get(indexInList).getStartPos() + ", " + open.get(indexInList).getEndPos() + ";;;;;");
            Pair<Boolean, Double> vertexUsableAndCost = IsVertexUsableAndCost(open.get(indexInList), pathMap, secondSystem);

            if (!vertexUsableAndCost.getFirst())
                open.remove(open.get(indexInList));

            else {
                //System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBB");
                double currentCost = vertexUsableAndCost.getSecond() + open.get(indexInList).getTimeCost();
                if (currentCost < minCost) {
                    minCost = currentCost;
                    indexOfLowestCost = indexInList;
                }
                indexInList++;
            }
        }

        return open.get(indexOfLowestCost);
    }

    private static Pair<Boolean, Double> IsVertexUsableAndCost(Vertex vertex, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap, SystemsPos secondSystem) {
        //System.out.println(vertex.getStartPos() + ", " + vertex.getEndPos() + ";;;;;" + vertex.smartGetSystemPos(secondSystem));
        if (vertex.smartIsPosFineForVertex(vertex.smartGetSystemPos(secondSystem))) {
            //System.out.println(vertex.getStartPos() + ", " + vertex.getEndPos() + ";;;;;");
            return new Pair<>(true, 0.0);
        }
        if (isSystem2CanMove(vertex, secondSystem) && isTherePossiblePosition(vertex, secondSystem)) {
            return new Pair<>(true, getCostOfOtherSystemPathAndAddToMap(vertex, pathMap, secondSystem));
        }
        return new Pair<>(false, 0.0);
    }

    private static Boolean isSystem2CanMove(Vertex vertex, SystemsPos secondSystem) {
        return getNode(vertex.smartGetSystemPos(secondSystem)).smartGetList(vertex.getStartPos(), GBNode.ListType.OUT).contains(vertex.getStartPos());
    }

    private static Boolean isTherePossiblePosition(Vertex vertex, SystemsPos secondSystem){
        LinkedList<SystemsPos> otherSystemPositions = new LinkedList<>(getAllSystemPositionsByPos(secondSystem));
        for (SystemsPos otherSystemPosition : otherSystemPositions) {
            if (vertex.smartIsPosFineForVertex(otherSystemPosition)) {
                return true;
            }
        }
        return false;
    }

    private static Double getCostOfOtherSystemPathAndAddToMap(Vertex vertex, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap, SystemsPos secondSystem) {
        SystemsPos pos = vertex.smartGetSystemPos(secondSystem);
        LinkedList<SystemsPos> list = vertex.smartMergeCommonNodes(secondSystem);
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
        vertex.smartSetSystemPos(path.get(path.size() - 1));
        return min;
    }

    private static Pair<LinkedList<SystemsPos>, Double> returnPath(Vertex vertex, HashMap<Vertex, Vertex> parents, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
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

    private static void addPathFromPathMap(Vertex current,  HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap, LinkedList<SystemsPos> pathList){
        for (int i = 0; i < pathMap.get(current).getFirst().size(); i++) {
            if (!pathList.get(i).equals(pathMap.get(current).getFirst().get(i)))
                pathList.add(i, pathMap.get(current).getFirst().get(i));
        }
    }

    private static Pair<LinkedList<SystemsPos>, Double> findPath(SystemsPos start, SystemsPos end, SystemsPos secondSystemPos) {
        LinkedList<Vertex> closedVer = new LinkedList<>();
        LinkedList<Vertex> openVer = new LinkedList<>();
        HashMap<Vertex, Vertex> parents = new HashMap<>();
        SystemsPos secondSystem = secondSystemPos;
        HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap = new HashMap<>();
        SystemsPos current = start;
        Vertex currentVer;

        for (SystemsPos neighbor : getNode(current).getNeighbors()) {
                openVer.add(new Vertex(current, neighbor, secondSystemPos));
        }

        while (!openVer.isEmpty()) {
            currentVer = getLowestCostVertex(openVer, pathMap, secondSystem);
            current = currentVer.getEndPos();
            openVer.remove(currentVer);
            closedVer.add(currentVer);

            if (current.equals(end)) {
                return returnPath(currentVer, parents, pathMap);
            }
            addNeighborsToOpen(current, closedVer, openVer, currentVer, parents, secondSystem);
        }

        return null;
    }

    public static LinkedList<SystemsPos> returnFinalPath(SystemsPos start, SystemsPos end, SystemsPos system2Pos) {
        return findPath(start, end, system2Pos).getFirst();
    }

    //TODO DELETE OR FIND SOLUTION
    public static void main(String[] args) {
        //LinkedList<SystemsPos> a = returnFinalPath(SystemsPos.GRIPER_OPEN, SystemsPos.GRIPER_CLOSE, SystemsPos.ARM_LOW);
        //printPath(a);
        printPath(getAllSystemsPositionsByNumber(2));
        printPath(getAllSystemPositions(systemName1, 2));
        printPath(getNode(ARM_ZG).getOtherSystemMustBeToEnter2());
    }
}

