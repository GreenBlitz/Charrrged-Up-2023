package edu.greenblitz.tobyDetermined.Nodesssss.Bla3;

import edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystemAlgorithm.Vertex;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;
import edu.wpi.first.math.Pair;

import java.util.HashMap;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.systemName1;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.systemName2;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemFunctions.getAllSystemsPositions;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemFunctions.getNode;

public class BlaBla {

    public static <T> void printPath(LinkedList<T> pathList) {
        for (T t : pathList) {
            System.out.print(t + ", ");
        }
        System.out.println();
    }

    public static void printPathVertex(LinkedList<BlaBlaVertex> pathList) {
        for (BlaBlaVertex vertex : pathList) {
            System.out.print(vertex.getStartPos() + ", " + vertex.getEndPos() + ";;;;;");
        }
        System.out.println();
    }

    private static <T> boolean isInList(T object, LinkedList<T> list) {
        return list.contains(object);
    }

    private static boolean NotInVertexList(LinkedList<BlaBlaVertex> verList, BlaBlaVertex ver) {
        for (BlaBlaVertex vertex : verList) {
            if (vertex.getStartPos().equals(ver.getStartPos())
                    && vertex.getEndPos().equals(ver.getEndPos())
                    && vertex.getSystem2Pos().equals(ver.getSystem2Pos())
                    && vertex.getSystem3Pos().equals(ver.getSystem3Pos())
            )
                return false;
        }
        return true;
    }

    public static boolean smartIsOnList(SystemsPos pos, SystemsPos posTarget) {
        if (pos.toString().contains(systemName1)) {
            if (posTarget.toString().contains(systemName2))
                return getNode(pos).getOtherSystemMustBeToOut2().contains(posTarget);
            return getNode(pos).getOtherSystemMustBeToOut3().contains(posTarget);
        }
        if (pos.toString().contains(systemName2)) {
            if (posTarget.toString().contains(systemName1))
                return getNode(pos).getOtherSystemMustBeToOut2().contains(posTarget);
            return getNode(pos).getOtherSystemMustBeToOut3().contains(posTarget);
        } else {
            if (posTarget.toString().contains(systemName1))
                return getNode(pos).getOtherSystemMustBeToOut2().contains(posTarget);
            return getNode(pos).getOtherSystemMustBeToOut3().contains(posTarget);
        }
    }

    public static void addNeighborsToOpen(SystemsPos current, LinkedList<BlaBlaVertex> closedVer, LinkedList<BlaBlaVertex> openVer, BlaBlaVertex currentVer, HashMap<BlaBlaVertex, BlaBlaVertex> parents) {
        for (SystemsPos neighbor : getNode(current).getNeighbors()) {
            if (NotInVertexList(closedVer, new BlaBlaVertex(current, neighbor, currentVer.getSystem2Pos(), currentVer.getSystem3Pos())) && NotInVertexList(openVer, new BlaBlaVertex(current, neighbor, currentVer.getSystem2Pos(), currentVer.getSystem3Pos()))) {
                openVer.add(new BlaBlaVertex(current, neighbor, currentVer.getSystem2Pos(), currentVer.getSystem3Pos()));
                parents.put(openVer.get(openVer.size() - 1), currentVer);
            }
        }
    }

    public static BlaBlaVertex getVertexLowestFcost(LinkedList<BlaBlaVertex> open, HashMap<BlaBlaVertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
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
            ;
        }
        return open.get(saveI);
    }

    public static Pair<Boolean, Double> IsVertexUsableAndCost(BlaBlaVertex blaBlaVertex, HashMap<BlaBlaVertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
        double cost = 0;
        boolean check = true;
        if (!blaBlaVertex.isPosFineForVertexSys2(blaBlaVertex.getSystem2Pos())) {
            check = isVertexUsable(blaBlaVertex, 2);
            if (check) {
                cost = getCostOfOtherSystemPathAndAddToMap(blaBlaVertex, pathMap, 2);
            }
        }

        if (!blaBlaVertex.isPosFineForVertexSys3(blaBlaVertex.getSystem3Pos())) {
            check = isVertexUsable(blaBlaVertex, 3);
            if (check) {
                cost = getCostOfOtherSystemPathAndAddToMap(blaBlaVertex, pathMap, 3);
            }
        }
        return new Pair<>(check, cost);
    }

    public static Boolean isVertexUsable(BlaBlaVertex blaBlaVertex, int sys) {
        LinkedList<SystemsPos> otherSystemPositions = getAllSystemsPositions();
        boolean isTherePossiblePosition = false;
        for (int i = 0; i < otherSystemPositions.size() && !isTherePossiblePosition; i++) {
            if (sys == 2) {
                if (blaBlaVertex.isPosFineForVertexSys2(otherSystemPositions.get(i))) {
                    isTherePossiblePosition = true;
                }
            } else {
                if (blaBlaVertex.isPosFineForVertexSys3(otherSystemPositions.get(i))) {
                    isTherePossiblePosition = true;
                }
            }
        }
        if (isTherePossiblePosition) {
            if (sys == 2)
                return smartIsOnList(blaBlaVertex.getSystem2Pos(), blaBlaVertex.getStartPos())
                        && smartIsOnList(blaBlaVertex.getSystem2Pos(), blaBlaVertex.getSystem3Pos());
            else {
                return smartIsOnList(blaBlaVertex.getSystem3Pos(), blaBlaVertex.getStartPos())
                        && smartIsOnList(blaBlaVertex.getSystem3Pos(), blaBlaVertex.getSystem3Pos());
            }
        }
        return false;
    }

    public static Double getCostOfOtherSystemPathAndAddToMap(BlaBlaVertex blaBlaVertex, HashMap<BlaBlaVertex, Pair<LinkedList<SystemsPos>, Double>> pathMap, int sys) {
        SystemsPos pos;
        LinkedList<SystemsPos> list;
        boolean check = (sys == 2);
        if (check) {
            pos = blaBlaVertex.getSystem2Pos();
            list = blaBlaVertex.mergeCommonNodes2();
        } else {
            pos = blaBlaVertex.getSystem3Pos();
            list = blaBlaVertex.mergeCommonNodes3();
        }
        double min = Double.MAX_VALUE;
        LinkedList<SystemsPos> path = new LinkedList<>();
        for (SystemsPos systemsPos : list) {
            Pair<LinkedList<SystemsPos>, Double> pair;
            if (check)
                pair = findPath(pos, systemsPos, blaBlaVertex.getStartPos(), blaBlaVertex.getSystem3Pos());
            else
                pair = findPath(pos, systemsPos, blaBlaVertex.getStartPos(), blaBlaVertex.getSystem2Pos());

            if (pair.getSecond() < min) {
                min = pair.getSecond();
                path = pair.getFirst();
            }
        }

        if (!check) {
            if (pathMap.get(blaBlaVertex).getFirst() != null)
                pathMap.get(blaBlaVertex).getFirst().addAll(path);
            else
                pathMap.put(blaBlaVertex, new Pair<>(path, min));
            blaBlaVertex.setSystem3Pos(path.getLast());

        } else {
            pathMap.put(blaBlaVertex, new Pair<>(path, min));
            blaBlaVertex.setSystem2Pos(path.getLast());
        }
        return min;
    }

    public static Pair<LinkedList<SystemsPos>, Double> returnPath(BlaBlaVertex vertex, HashMap<BlaBlaVertex, BlaBlaVertex> parents, HashMap<BlaBlaVertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
        LinkedList<SystemsPos> pathList = new LinkedList<>();
        double totalCost = 0;
        BlaBlaVertex current = vertex;
        while (current != null) {
            totalCost += current.getTimeCost();
            pathList.addFirst(current.getEndPos());
            pathList.addFirst(current.getStartPos());
            if (pathMap.containsKey(current)) {
                totalCost += pathMap.get(current).getSecond();
                for (int i = 0; i < pathMap.get(current).getFirst().size(); i++) {
                    if (!pathList.get(i).equals(pathMap.get(current).getFirst().get(i)))
                        pathList.add(i, pathMap.get(current).getFirst().get(i));
                }
            }
            current = parents.get(current);
        }
        return new Pair<>(pathList, totalCost);
    }

    public static Pair<LinkedList<SystemsPos>, Double> findPath(SystemsPos start, SystemsPos end, SystemsPos system2Pos, SystemsPos system3Pos) {
        LinkedList<BlaBlaVertex> closedVer = new LinkedList<>();
        LinkedList<BlaBlaVertex> openVer = new LinkedList<>();
        HashMap<BlaBlaVertex, BlaBlaVertex> parents = new HashMap<>();
        HashMap<BlaBlaVertex, Pair<LinkedList<SystemsPos>, Double>> pathMap = new HashMap<>();
        SystemsPos current = start;
        BlaBlaVertex currentVer;

        for (SystemsPos neighbor : getNode(current).getNeighbors()) {
            openVer.add(new BlaBlaVertex(current, neighbor, system2Pos, system3Pos));
        }

        while (!openVer.isEmpty()) {
            currentVer = getVertexLowestFcost(openVer, pathMap);
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

    public static LinkedList<SystemsPos> printAndReturnFinalPath(SystemsPos start, SystemsPos end, SystemsPos system2Pos, SystemsPos system3Pos) {
        Pair<LinkedList<SystemsPos>, Double> finalPathAndCost = findPath(start, end, system2Pos, system3Pos);
        printPath(finalPathAndCost.getFirst());
        System.out.println("cost: "+finalPathAndCost.getSecond());
        return finalPathAndCost.getFirst();
    }

    public static void main(String[] args) {
        LinkedList<SystemsPos> a = printAndReturnFinalPath(SystemsPos.CLIMBING_ROMY, SystemsPos.CLIMBING_NOAM, SystemsPos.ARM_HIGH, SystemsPos.GRIPER_CLOSE);
    }

}