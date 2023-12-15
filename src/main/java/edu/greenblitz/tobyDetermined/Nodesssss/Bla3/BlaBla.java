//package edu.greenblitz.tobyDetermined.Nodesssss.Bla3;
//
//import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;
//import edu.wpi.first.math.Pair;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//
//import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos.ARM_HIGH;
//import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemFunctions.*;
//
//// TODO , MAKE IT EXTENDS AStarVertex
//public class BlaBla {
//    //TODO , CHECK IN FUNCTIONS
//    public static void printPathVertex(LinkedList<BlaBlaVertex> pathList) {
//        for (BlaBlaVertex vertex : pathList) {
//            System.out.print(vertex.getStartPos() + ", " + vertex.getEndPos() + ";;;;;");
//        }
//        System.out.println();
//    }
//    //TODO , CHECK IN FUNCTIONS
//    private static boolean NotInVertexList(LinkedList<BlaBlaVertex> verList, BlaBlaVertex ver) {
//        for (BlaBlaVertex vertex : verList) {
//            if (vertex.getStartPos().equals(ver.getStartPos())
//                    && vertex.getEndPos().equals(ver.getEndPos())
//                    && vertex.getSystem2Pos().equals(ver.getSystem2Pos())
//                    && vertex.getSystem3Pos().equals(ver.getSystem3Pos())
//            )
//                return false;
//        }
//        return true;
//    }
//    //TODO , CHECK IN FUNCTIONS
//    public static void addNeighborsToOpen(SystemsPos current, LinkedList<BlaBlaVertex> closedVer, LinkedList<BlaBlaVertex> openVer, BlaBlaVertex currentVer, HashMap<BlaBlaVertex, BlaBlaVertex> parents) {
//        for (SystemsPos neighbor : getNode(current).getNeighbors()) {
//            if (NotInVertexList(closedVer, new BlaBlaVertex(current, neighbor, currentVer.getSystem2Pos(), currentVer.getSystem3Pos())) && NotInVertexList(openVer, new BlaBlaVertex(current, neighbor, currentVer.getSystem2Pos(), currentVer.getSystem3Pos()))) {
//                openVer.add(new BlaBlaVertex(current, neighbor, currentVer.getSystem2Pos(), currentVer.getSystem3Pos()));
//                parents.put(openVer.get(openVer.size() - 1), currentVer);
//            }
//        }
//    }
//
//    public static BlaBlaVertex getLowestCostVertex(LinkedList<BlaBlaVertex> open, HashMap<BlaBlaVertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
//        int saveI = 0;
//        int index = 0;
//        double minFCost = Double.MAX_VALUE;
//        while (index != open.size()) {
//            Pair<Boolean, Double> vertexUsableAndCost = IsVertexUsableAndCost(open.get(index), pathMap);
//            if (!vertexUsableAndCost.getFirst()) {
//                open.remove(open.get(index));
//            }
//            else {
//                double currentFCost = vertexUsableAndCost.getSecond();
//                currentFCost += open.get(index).getTimeCost();
//                if (currentFCost < minFCost) {
//                    minFCost = currentFCost;
//                    saveI = index;
//                }
//                index++;
//            }
//        }
//        return open.get(saveI);
//    }
//
//    public static Pair<Boolean, Double> IsVertexUsableAndCost(BlaBlaVertex blaBlaVertex, HashMap<BlaBlaVertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
//        double cost = 0;
//        boolean check;
//        Pair<Double, Boolean> vertexUsableAndCost = isVertexUsableAndCostSystem2(blaBlaVertex, pathMap);
//        cost+=vertexUsableAndCost.getFirst();
//        check = vertexUsableAndCost.getSecond();
//
//        vertexUsableAndCost = isVertexUsableAndCostSystem3(blaBlaVertex, pathMap);
//        cost+=vertexUsableAndCost.getFirst();
//        check = check && vertexUsableAndCost.getSecond();
//
//        return new Pair<>(check, cost);
//    }
//    public static Pair<Double,Boolean> isVertexUsableAndCostSystem2(BlaBlaVertex blaBlaVertex, HashMap<BlaBlaVertex, Pair<LinkedList<SystemsPos>, Double>> pathMap){
//        if (!blaBlaVertex.isPosFineForVertexSystem2(blaBlaVertex.getSystem2Pos())) {
//            int systemNumber = 2;
//            boolean check = isVertexUsable(blaBlaVertex, systemNumber);
//            if (check) {
//                Pair<Double, LinkedList<SystemsPos>> pathAndCost = getAnotherSystemPathAndCost(blaBlaVertex, pathMap, systemNumber);
//                addToMapBySystemNumber(pathMap, pathAndCost.getSecond(), blaBlaVertex, pathAndCost.getFirst(), systemNumber);
//                return new Pair<>(pathAndCost.getFirst(), true);
//            }
//            return new Pair<>(0.0, false);
//        }
//        return new Pair<>(0.0, true);
//    }
//    public static Pair<Double,Boolean> isVertexUsableAndCostSystem3(BlaBlaVertex blaBlaVertex, HashMap<BlaBlaVertex, Pair<LinkedList<SystemsPos>, Double>> pathMap){
//        if (!blaBlaVertex.isPosFineForVertexSystem3(blaBlaVertex.getSystem3Pos())) {
//            int systemNumber = 3;
//            boolean check = isVertexUsable(blaBlaVertex, systemNumber);
//            if (check) {
//                Pair<Double, LinkedList<SystemsPos>> pathAndCost = getAnotherSystemPathAndCost(blaBlaVertex, pathMap, systemNumber);
//                addToMapBySystemNumber(pathMap, pathAndCost.getSecond(), blaBlaVertex, pathAndCost.getFirst(), systemNumber);
//                return new Pair<>(pathAndCost.getFirst(), true);
//            }
//            return new Pair<>(0.0, false);
//        }
//        return new Pair<>(0.0, true);
//    }
//    public static boolean isVertexUsable(BlaBlaVertex blaBlaVertex, int systemNumber) {
//        return isWantedSystemCanMove(blaBlaVertex, systemNumber) && isTherePossiblePositionForWantedSystem(blaBlaVertex ,systemNumber);
//    }
//
//    public static boolean isTherePossiblePositionForWantedSystem(BlaBlaVertex blaBlaVertex, int systemNumber){
//        if (systemNumber == 2)
//            return isTherePossiblePositionSystem2(blaBlaVertex);
//        return isTherePossiblePositionSystem3(blaBlaVertex);
//    }
//    public static boolean isTherePossiblePositionSystem2(BlaBlaVertex blaBlaVertex) {
//        LinkedList<SystemsPos> otherSystemPositions = getAllSystemsPositions();
//        for (SystemsPos otherSystemPosition : otherSystemPositions) {
//            if (blaBlaVertex.isPosFineForVertexSystem2(otherSystemPosition))
//                return true;
//        }
//        return false;
//    }
//    public static boolean isTherePossiblePositionSystem3(BlaBlaVertex blaBlaVertex){
//        LinkedList<SystemsPos> otherSystemPositions = getAllSystemsPositions();
//        for (SystemsPos otherSystemPosition : otherSystemPositions) {
//            if (blaBlaVertex.isPosFineForVertexSystem3(otherSystemPosition))
//                return true;
//        }
//        return false;
//    }
//
//    public static boolean isWantedSystemCanMove(BlaBlaVertex blaBlaVertex, int systemNumber){
//        if (systemNumber == 2)
//            return isSystem2CanMove(blaBlaVertex);
//        return isSystem3CanMove(blaBlaVertex);
//    }
//    public static boolean isSystem2CanMove(BlaBlaVertex blaBlaVertex) {
//        return smartIsOnList(blaBlaVertex.getSystem2Pos(), blaBlaVertex.getStartPos())
//                && smartIsOnList(blaBlaVertex.getSystem2Pos(), blaBlaVertex.getSystem3Pos());
//    }
//    public static boolean isSystem3CanMove(BlaBlaVertex blaBlaVertex) {
//        return smartIsOnList(blaBlaVertex.getSystem3Pos(), blaBlaVertex.getStartPos())
//                && smartIsOnList(blaBlaVertex.getSystem3Pos(), blaBlaVertex.getSystem2Pos());
//    }
//
//    public static void addToMapBySystemNumber(HashMap<BlaBlaVertex, Pair<LinkedList<SystemsPos>, Double>> pathMap, LinkedList<SystemsPos> path, BlaBlaVertex blaBlaVertex, double cost, int systemNumber){
//        if (systemNumber == 2)
//            addToMapSystem2(pathMap, path, blaBlaVertex, cost);
//        else
//            addToMapSystem3(pathMap, path, blaBlaVertex, cost);
//    }
//    public static void addToMapSystem2(HashMap<BlaBlaVertex, Pair<LinkedList<SystemsPos>, Double>> pathMap, LinkedList<SystemsPos> path, BlaBlaVertex blaBlaVertex, double cost){
//            if (pathMap.get(blaBlaVertex).getFirst() != null)
//                pathMap.get(blaBlaVertex).getFirst().addAll(path);
//            else
//                pathMap.put(blaBlaVertex, new Pair<>(path, cost));
//            blaBlaVertex.setSystem3Pos(path.getLast());
//    }
//    public static void addToMapSystem3(HashMap<BlaBlaVertex, Pair<LinkedList<SystemsPos>, Double>> pathMap, LinkedList<SystemsPos> path, BlaBlaVertex blaBlaVertex, double cost){
//        pathMap.put(blaBlaVertex, new Pair<>(path, cost));
//        blaBlaVertex.setSystem2Pos(path.getLast());
//    }
//
//
//    public static Pair<Double, LinkedList<SystemsPos>> getAnotherSystemPathAndCost(BlaBlaVertex blaBlaVertex, HashMap<BlaBlaVertex, Pair<LinkedList<SystemsPos>, Double>> pathMap, int systemNumber) {
//        SystemsPos newStartPos;
//        LinkedList<SystemsPos> list;
//        SystemsPos lastVariable;
//        if (systemNumber == 2){
//            newStartPos = blaBlaVertex.getSystem2Pos();
//            list = new LinkedList<>(blaBlaVertex.mergeCommonNodes2());
//            lastVariable = blaBlaVertex.getSystem3Pos();
//        }
//        else {
//            newStartPos = blaBlaVertex.getSystem3Pos();
//            list = new LinkedList<>(blaBlaVertex.mergeCommonNodes3());
//            lastVariable = blaBlaVertex.getSystem2Pos();
//        }
//
//        double min = Double.MAX_VALUE;
//        LinkedList<SystemsPos> path = new LinkedList<>();
//
//        for (SystemsPos systemsPos : list) {
//            System.out.println(newStartPos+";;;;;; "+systemsPos+";;;;"+blaBlaVertex.getStartPos()+";;;;;"+lastVariable);
//            Pair<LinkedList<SystemsPos>, Double> pair = findPath(newStartPos, systemsPos, blaBlaVertex.getStartPos(), lastVariable);
//            if (pair.getSecond() < min) {
//                min = pair.getSecond();
//                path = pair.getFirst();
//            }
//        }
//
//        return new Pair<>(min, path);
//    }
//
//    public static Pair<LinkedList<SystemsPos>, Double> returnPath(BlaBlaVertex vertex, HashMap<BlaBlaVertex, BlaBlaVertex> parents, HashMap<BlaBlaVertex, Pair<LinkedList<SystemsPos>, Double>> pathMap) {
//        LinkedList<SystemsPos> pathList = new LinkedList<>();
//        double totalCost = 0;
//        BlaBlaVertex current = vertex;
//        while (current != null) {
//            totalCost += current.getTimeCost();
//            pathList.addFirst(current.getEndPos());
//            pathList.addFirst(current.getStartPos());
//            if (pathMap.containsKey(current)) {
//                totalCost += pathMap.get(current).getSecond();
//                for (int i = 0; i < pathMap.get(current).getFirst().size(); i++) {
//                    if (!pathList.get(i).equals(pathMap.get(current).getFirst().get(i)))
//                        pathList.add(i, pathMap.get(current).getFirst().get(i));
//                }
//            }
//            current = parents.get(current);
//        }
//        return new Pair<>(pathList, totalCost);
//    }
//
//    public static Pair<LinkedList<SystemsPos>, Double> findPath(SystemsPos start, SystemsPos end, SystemsPos system2Pos, SystemsPos system3Pos) {
//        LinkedList<BlaBlaVertex> closedVer = new LinkedList<>();
//        LinkedList<BlaBlaVertex> openVer = new LinkedList<>();
//        HashMap<BlaBlaVertex, BlaBlaVertex> parents = new HashMap<>();
//        HashMap<BlaBlaVertex, Pair<LinkedList<SystemsPos>, Double>> pathMap = new HashMap<>();
//        SystemsPos current = start;
//        BlaBlaVertex currentVer;
//
//        for (SystemsPos neighbor : getNode(current).getNeighbors()) {
//            openVer.add(new BlaBlaVertex(current, neighbor, system2Pos, system3Pos));
//        }
//
//        while (!openVer.isEmpty()) {
//            currentVer = getLowestCostVertex(openVer, pathMap);
//            current = currentVer.getEndPos();
//            openVer.remove(currentVer);
//            closedVer.add(currentVer);
//
//            if (current.equals(end)) {
//                return returnPath(currentVer, parents, pathMap);
//            }
//            addNeighborsToOpen(current, closedVer, openVer, currentVer, parents);
//        }
//        return null;
//    }
//
//    public static LinkedList<SystemsPos> returnFinalPath(SystemsPos start, SystemsPos end, SystemsPos system2Pos, SystemsPos system3Pos) {
//        return findPath(start, end, system2Pos, system3Pos).getFirst();
//    }
//
//    //TODO DELETE OR FIND SOLUTION
//    public static void main(String[] args) {
//        LinkedList<SystemsPos> a = returnFinalPath(SystemsPos.CLIMBING_ROMY, SystemsPos.CLIMBING_NOAM, ARM_HIGH, SystemsPos.GRIPER_CLOSE);
//    }
//
//}