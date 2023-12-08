package edu.greenblitz.tobyDetermined.Nodesssss.MultiNodeSubsystem;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.wpi.first.math.Pair;

import java.util.HashMap;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos;

public class AStarVertex {

    public static <T> void printPath(LinkedList<T> pathList) {
        for (int i = 0; i < pathList.size(); i++) {
            System.out.print(pathList.get(i) + ", ");
        }
        System.out.println();
    }

    public static void printPathVertex(LinkedList<Vertex> pathList) {
        for (int i = 0; i < pathList.size(); i++) {
            System.out.print(pathList.get(i).getPos1() + ", " + pathList.get(i).getPos2() + ";;;;;");
        }
        System.out.println();
    }

    private static <T> boolean isInList(T object, LinkedList<T> list) {
        return list.contains(object);
    }

    private static boolean NotInVertexList(LinkedList<Vertex> verList, Vertex ver) {
        for (Vertex vertex : verList) {
            if (vertex.getPos1().equals(ver.getPos1()) && vertex.getPos2().equals(ver.getPos2()))
                return false;
        }
        return true;
    }

    public static void addNeighborsToOpen(SystemsPos current, LinkedList<Vertex> closedVer, LinkedList<Vertex> openVer, Vertex currentVer, HashMap<Vertex, Vertex> parents){
        for (SystemsPos neighbor : NodeBase.getNode(current).getNeighbors()) {
            if (NotInVertexList(closedVer, new Vertex(current, neighbor, currentVer.getOtherSystemPos())) && NotInVertexList(openVer, new Vertex(current, neighbor, currentVer.getOtherSystemPos()))) {
                openVer.add(new Vertex(current, neighbor, currentVer.getOtherSystemPos()));
                parents.put(openVer.get(openVer.size() - 1), currentVer);
            }
        }
    }

    public static Vertex getVertexLowestFcost(LinkedList<Vertex> open, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
        int saveI = 0;
        int index = 0;
        double minFCost = Double.MAX_VALUE;
        while (index != open.size()) {
            Pair<Boolean, Double> a = returnIsVertexUsableAndCost(open.get(index), pathMap);
            if (!a.getFirst()) {
                open.remove(open.get(index));
            }
            else {
                double currentFCost = a.getSecond();
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

    public static Pair<Boolean, Double> returnIsVertexUsableAndCost(Vertex vertex, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
        double cost = 0;
        boolean check = true;
        if (!vertex.isPosFineForVertex(vertex.getOtherSystemPos())) {
            check = isVertexUsable(vertex);
            if (check)
                cost =  getCostOfOtherSystemPathAndAddToMap(vertex, pathMap);
        }
        return new Pair<>(check,cost);
    }

    public static Boolean isVertexUsable(Vertex vertex) {
        LinkedList<SystemsPos> otherSystemPositions = NodeBase.getAllSystemsPositions();
        boolean isTherePossiblePosition = false;
        for (int i = 0; i < otherSystemPositions.size() && !isTherePossiblePosition; i++) {
            if (vertex.isPosFineForVertex(otherSystemPositions.get(i))) {
                isTherePossiblePosition = true;
            }
        }
        if (isTherePossiblePosition) {
            return NodeBase.getNode(vertex.getOtherSystemPos()).getOtherSystemMustBeToOut().contains(vertex.getPos1())
                    || NodeBase.getNode(vertex.getOtherSystemPos()).getOtherSystemMustBeToOut().isEmpty();
        }
        return false;
    }

    public static Double getCostOfOtherSystemPathAndAddToMap(Vertex vertex, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
        SystemsPos pos = vertex.getOtherSystemPos();
        LinkedList<SystemsPos> list = vertex.mergeAndGetPos1OutAndPos2Enter();
        double min = Double.MAX_VALUE;
        LinkedList<SystemsPos> path = new LinkedList<>();
        for (SystemsPos systemsPos : list) {
            Pair<LinkedList<SystemsPos>, Double> pair = getPath(pos, systemsPos, vertex.getPos1());
            if (pair.getSecond() < min) {
                min = pair.getSecond();
                path = pair.getFirst();
            }
        }
        pathMap.put(vertex, new Pair<>(path, min));
        vertex.setOtherSystemPos(path.get(path.size() - 1));
        return min;
    }

    public static Pair<LinkedList<SystemsPos>, Double> returnPath(Vertex vertex, HashMap<Vertex, Vertex> parents, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
        LinkedList<SystemsPos> pathList = new LinkedList<>();
        double totalCost = 0;
        Vertex current = vertex;
        pathList.addFirst(current.getPos2());
        while (current != null) {
            totalCost += current.getTimeCost();
            if (!pathList.get(0).equals(current.getPos2()))
                pathList.addFirst(current.getPos2());
            if (pathMap.containsKey(current)) {
                totalCost += pathMap.get(current).getSecond();
                for (int i = 0; i < pathMap.get(current).getFirst().size(); i++) {
                    if (!pathList.get(i).equals(pathMap.get(current).getFirst().get(i)))
                        pathList.add(i, pathMap.get(current).getFirst().get(i));
                }
            }
            if (!pathList.get(0).equals(current.getPos1()))
                pathList.addFirst(current.getPos1());
            current = parents.get(current);
        }
        return new Pair<>(pathList, totalCost);
    }

    public static Pair<LinkedList<SystemsPos>, Double> getPath(SystemsPos start, SystemsPos end, SystemsPos secondSystemPos) {
        LinkedList<Vertex> closedVer = new LinkedList<>();
        LinkedList<Vertex> openVer = new LinkedList<>();
        HashMap<Vertex, Vertex> parents = new HashMap<>();
        HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap = new HashMap<>();
        SystemsPos current = start;
        Vertex currentVer;

        for (SystemsPos neighbor : NodeBase.getNode(current).getNeighbors()) {
            if (NotInVertexList(closedVer, new Vertex(current, neighbor, secondSystemPos)) && NotInVertexList(openVer, new Vertex(current, neighbor, secondSystemPos))) {
                openVer.add(new Vertex(current, neighbor, secondSystemPos));
            }
        }

        while (!openVer.isEmpty()) {

            currentVer = getVertexLowestFcost(openVer, pathMap);
            current = currentVer.getPos2();
            openVer.remove(currentVer);
            closedVer.add(currentVer);

            if (current.equals(end)) {
                return returnPath(currentVer, parents, pathMap);
            }
            addNeighborsToOpen(current, closedVer, openVer, currentVer, parents);
        }
        return null;
    }

    public static LinkedList<SystemsPos> printAndReturnFinalPath(SystemsPos start, SystemsPos end, SystemsPos secondSystemState) {
        Pair<LinkedList<SystemsPos>, Double> finalPathAndCost = getPath(start, end, secondSystemState);
        printPath(finalPathAndCost.getFirst());
        System.out.println(finalPathAndCost.getSecond());
        return finalPathAndCost.getFirst();
    }

    public static void main(String[] args) {
        LinkedList<SystemsPos> a = printAndReturnFinalPath(SystemsPos.ARM_LOWWW, SystemsPos.ARM_HIGH, SystemsPos.GRIPER_CLOSE);
    }
}

