package edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystemAlgorithm;

import edu.wpi.first.math.Pair;
import java.util.HashMap;
import java.util.LinkedList;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemFunctions.*;

public class AStarVertex3 {

    public static <T> void printPath(LinkedList<T> pathList) {
        for (T t : pathList) {
            System.out.print(t + ", ");
        }
        System.out.println();
    }

    public static void printPathVertex(LinkedList<Vertex> pathList) {
        for (Vertex vertex : pathList) {
            System.out.print(vertex.getPos1() + ", " + vertex.getPos2() + ";;;;;");
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

    public static void addNeighborsToOpen(SystemsPos current, LinkedList<Vertex> closedVer, LinkedList<Vertex> openVer, Vertex currentVer, HashMap<Vertex, Vertex> parents) {
        for (SystemsPos neighbor : getNode(current).getNeighbors()) {
            if (NotInVertexList(closedVer, new Vertex(current, neighbor, currentVer.getOtherSystemPos1())) && NotInVertexList(openVer, new Vertex(current, neighbor, currentVer.getOtherSystemPos1()))) {
                openVer.add(new Vertex(current, neighbor, currentVer.getOtherSystemPos1()));
                parents.put(openVer.get(openVer.size() - 1), currentVer);
            }
        }
    }

    public static Vertex getVertexLowestFcost(LinkedList<Vertex> open, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
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
            };
        }
        return open.get(saveI);
    }

    public static Pair<Boolean, Double> IsVertexUsableAndCost(Vertex vertex, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
        double cost = 0;
        boolean check = true;
        if (!vertex.isPosFineForVertex(vertex.getOtherSystemPos1())) {
            check = isVertexUsable(vertex);
            if (check)
                cost = getCostOfOtherSystemPathAndAddToMap(vertex, pathMap);
        }
        return new Pair<>(check, cost);
    }

    public static Boolean isVertexUsable(Vertex vertex) {
        LinkedList<SystemsPos> otherSystemPositions = getAllSystemsPositions();
        boolean isTherePossiblePosition = false;
        for (int i = 0; i < otherSystemPositions.size() && !isTherePossiblePosition; i++) {
            if (vertex.isPosFineForVertex(otherSystemPositions.get(i))) {
                isTherePossiblePosition = true;
            }
        }
        if (isTherePossiblePosition) {
            return getNode(vertex.getOtherSystemPos1()).getOtherSystemMustBeToOut1().contains(vertex.getPos1())
                    || getNode(vertex.getOtherSystemPos1()).getOtherSystemMustBeToOut1().isEmpty();
        }
        return false;
    }

    public static Double getCostOfOtherSystemPathAndAddToMap(Vertex vertex, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
        SystemsPos pos = vertex.getOtherSystemPos1();
        LinkedList<SystemsPos> list = vertex.mergeCommonNodes();
        double min = Double.MAX_VALUE;
        LinkedList<SystemsPos> path = new LinkedList<>();
        for (SystemsPos systemsPos : list) {
            Pair<LinkedList<SystemsPos>, Double> pair = findPath(pos, systemsPos, vertex.getPos1());
            if (pair.getSecond() < min) {
                min = pair.getSecond();
                path = pair.getFirst();
            }
        }
        pathMap.put(vertex, new Pair<>(path, min));
        vertex.setOtherSystemPos1(path.get(path.size() - 1));
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

    public static Pair<LinkedList<SystemsPos>, Double> findPath(SystemsPos start, SystemsPos end, SystemsPos secondSystemPos) {
        LinkedList<Vertex> closedVer = new LinkedList<>();
        LinkedList<Vertex> openVer = new LinkedList<>();
        HashMap<Vertex, Vertex> parents = new HashMap<>();
        HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap = new HashMap<>();
        SystemsPos current = start;
        Vertex currentVer;

        for (SystemsPos neighbor : getNode(current).getNeighbors()) {
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
        Pair<LinkedList<SystemsPos>, Double> finalPathAndCost = findPath(start, end, secondSystemState);
        printPath(finalPathAndCost.getFirst());
        System.out.println(finalPathAndCost.getSecond());
        return finalPathAndCost.getFirst();
    }

    public static void main(String[] args) {
        LinkedList<SystemsPos> a = printAndReturnFinalPath(SystemsPos.ARM_LOW, SystemsPos.ARM_HIGH, SystemsPos.GRIPER_OPEN);
//        NodeArm a = new NodeArm(0,'r'+'o'+'m'+'y'+' '+'i'+'s'+' '+'a'+'u'+'t'+'i'+'s'+'t'+'i'+'c');
    }
}