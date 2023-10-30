package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.wpi.first.math.Pair;

import java.util.HashMap;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos;

public class AStarVertex {

    public static <T> void printPath(LinkedList<T> pathList) {
        for (int i = 0; i <= pathList.size() - 1; i++) {
            System.out.print(pathList.get(i) + ", ");
        }
        System.out.println();
    }

    public static void printPathVer(LinkedList<Vertex> pathList) {
        for (int i = 0; i <= pathList.size() - 1; i++) {
            System.out.print(pathList.get(i).getPos1() + ", " + pathList.get(i).getPos2() + ";;;;;");
        }
        System.out.println();
    }

    private static <T> boolean isInList(T object, LinkedList<T> list) {
        return list.contains(object);
    }

    private static boolean ifInVerList(LinkedList<Vertex> verList, Vertex ver) {
        for (Vertex vertex : verList) {
            if (vertex.getPos1().equals(ver.getPos1()) && vertex.getPos2().equals(ver.getPos2()))
                return true;
        }
        return false;
    }

    public static Vertex getVertexLowestFcost(LinkedList<Vertex> open, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
        int saveI = 0;
        int index = 0;
        double minFCost = Double.MAX_VALUE;
        while (index != open.size()) {
            Pair<Double, Boolean> isVertexFineAndCost = addOtherSystemCost(open.get(index), pathMap);
            if (!isVertexFineAndCost.getSecond()) {
                open.remove(open.get(index));
            } else {
                double currentFCost = isVertexFineAndCost.getFirst();
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

    public static Pair<Double, Boolean> addOtherSystemCost(Vertex vertex, HashMap<Vertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
        if (!vertex.inOtherSystemMustBe(vertex.getOtherSystemPos())) {
            LinkedList<SystemsPos> otherSystemPositions = NodeBase.getOneSystemPositions();
            boolean isTherePossiblePosition = false;
            for (int i = 0; i < otherSystemPositions.size() && !isTherePossiblePosition; i++) {
                if (vertex.inOtherSystemMustBe(otherSystemPositions.get(i))) {
                    isTherePossiblePosition = true;
                }
            }
            if (isTherePossiblePosition) {
                if (NodeBase.getNode(vertex.getOtherSystemPos()).getOtherSystemMustBe().contains(vertex.getPos1()) || NodeBase.getNode(vertex.getOtherSystemPos()).getOtherSystemMustBe().isEmpty()) {
                    SystemsPos pos = vertex.getOtherSystemPos();
                    LinkedList<SystemsPos> list = vertex.mergeAndGetOtherSystemMustBe();
                    double min = Double.MAX_VALUE;
                    LinkedList<SystemsPos> path = new LinkedList<>();
                    for (SystemsPos griperPos : list) {
                        Pair<LinkedList<SystemsPos>, Double> pair = getPath(pos, griperPos, vertex.getPos1());
                        if (pair.getSecond() < min) {
                            min = pair.getSecond();
                            path = pair.getFirst();
                        }
                    }
                    pathMap.put(vertex, new Pair<>(path, min));
                    vertex.setOtherSystemPos(path.get(path.size() - 1));
                    return new Pair<>(min, true);
                }
                return new Pair<>(0.0, false);
            }
            return new Pair<>(0.0, false);
        }
        return new Pair<>(0.0, true);
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
            if (!ifInVerList(closedVer, new Vertex(current, neighbor, secondSystemPos)) && !ifInVerList(openVer, new Vertex(current, neighbor, secondSystemPos))) {
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
            for (SystemsPos neighbor : NodeBase.getNode(current).getNeighbors()) {
                if (!ifInVerList(closedVer, new Vertex(current, neighbor, currentVer.getOtherSystemPos())) && !ifInVerList(openVer, new Vertex(current, neighbor, currentVer.getOtherSystemPos()))) {
                    openVer.add(new Vertex(current, neighbor, currentVer.getOtherSystemPos()));
                    parents.put(openVer.get(openVer.size() - 1), currentVer);
                }
            }
        }
        return null;
    }

    public static LinkedList<SystemsPos> printAndReturnFinalPath(SystemsPos start, SystemsPos end, SystemsPos secondSystemState) {
        Pair<LinkedList<SystemsPos>, Double> a = getPath(start, end, secondSystemState);
        printPath(a.getFirst());
        System.out.println(a.getSecond());
        return a.getFirst();
    }

    public static void main(String[] args) {
        LinkedList<SystemsPos> a = printAndReturnFinalPath(SystemsPos.ARM_LOWWW, SystemsPos.ARM_HIGH, SystemsPos.GRIPER_CLOSE);
    }
}

