package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.wpi.first.math.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static edu.greenblitz.tobyDetermined.RobotMap.Intake.SystemsPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.Intake.SystemsPos;


public class AStar {

    public static <T> void printPath(LinkedList<T> pathList) {
        for (int i = 0; i <= pathList.size() - 1; i++) {
            System.out.print(pathList.get(i) + ", ");
        }
        System.out.println();
    }

    public static double getDistanceToStartPlusEnd(SystemsPos current, SystemsPos start, SystemsPos end) {
        double gCost = NodeBase.getNode(current).getCost(start);
        double hCost = NodeBase.getNode(current).getCost(end);
        return gCost + hCost;

    }

    public static Pair<Double, SystemsPos> addAnotherPathAndCost(SystemsPos closed, SystemsPos current, SystemsPos griperPos, HashMap<SystemsPos, LinkedList<SystemsPos>> griperPath) {
        SystemsPos holder = NodeBase.getGripPos(current);
        // System.out.println(current+", "+griperPos);
        if (!NodeBase.getNode(holder).getOtherSystemMustBe().isEmpty() && !NodeBase.getNode(holder).getOtherSystemMustBe().contains(griperPos)) {
            if (!NodeBase.getNode(closed).getOtherSystemMustBe().isEmpty() && !NodeBase.getNode(closed).getOtherSystemMustBe().contains(griperPos)) {
                double min = Double.MAX_VALUE;
                LinkedList<SystemsPos> path = new LinkedList<>();
                for (int i = 0; i < NodeBase.getNode(holder).getOtherSystemMustBe().size(); i++) {
                    Pair<LinkedList<SystemsPos>, Double> list = getPath(griperPos, NodeBase.getNode(holder).getOtherSystemMustBe().get(i), holder);
                    if (min > list.getSecond()) {
                        min = list.getSecond();
                        path = list.getFirst();
                    }
                }
                griperPath.put(holder, path);
                return new Pair<>(min, holder);
            }
        }
        return new Pair<>(0.0, current);
    }

    public static SystemsPos getLowestFcost(LinkedList<SystemsPos> closed, LinkedList<SystemsPos> open, SystemsPos start, SystemsPos end, SystemsPos griperPos, HashMap<SystemsPos, LinkedList<SystemsPos>> griperPath) {
        if (open.isEmpty()) {
            return null; // Handle the case where the list is empty
        }
        int saveI = 0;
        double fCost = getDistanceToStartPlusEnd(open.get(0), start, end);
        //null
        for (int i = 1; i < open.size(); i++) {
            double currentFCost = getDistanceToStartPlusEnd(open.get(i), start, end);
            if (currentFCost < fCost) {
                fCost = currentFCost;
                saveI = i;

            }
        }
        //cost += fCost;
        return open.get(saveI);
    }

    public static Pair<LinkedList<SystemsPos>, Double> returnPath(SystemsPos nodeArm, Map<SystemsPos, SystemsPos> parents, HashMap<SystemsPos, LinkedList<SystemsPos>> griperPath, SystemsPos end) {
        LinkedList<SystemsPos> pathList = new LinkedList<>();
        SystemsPos current = nodeArm;
        while (current != null) {
            pathList.addFirst(current);
            if (griperPath.containsKey(current)) {
                for (int i = 0; i < griperPath.get(current).size(); i++) {
                    pathList.add(i, griperPath.get(current).get(i));
                }
            }
            current = parents.get(current);
        }
        //printPath(pathList);
        return new Pair<>(pathList, 0.0);
    }

    public static Pair<LinkedList<SystemsPos>, Double> getPath(SystemsPos start, SystemsPos end, SystemsPos gripPos) {
        LinkedList<SystemsPos> nodesCanGoTo = new LinkedList<>();
        LinkedList<SystemsPos> closed = new LinkedList<>();
        Map<SystemsPos, SystemsPos> parents = new HashMap<>();
        HashMap<SystemsPos, LinkedList<SystemsPos>> griperPath = new HashMap<>();
        double cost = 0;
        nodesCanGoTo.add(start);
        closed.add(start);
        while (!nodesCanGoTo.isEmpty()) {

            SystemsPos current;
            if (!closed.contains(nodesCanGoTo.get(0))) {
                current = getLowestFcost(closed, nodesCanGoTo, start, end, gripPos, griperPath);
            } else {
                current = nodesCanGoTo.get(0);
            }
            nodesCanGoTo.remove(current);
            if (!closed.contains(current))
                closed.add(current);

            if (current.equals(end)) {
                return returnPath(current, parents, griperPath, end);
            }

            for (SystemsPos neighbor : NodeBase.getNode(current).getNeighbors()) {
                if (!isInList(neighbor, closed) && !isInList(neighbor, nodesCanGoTo)) {
                    nodesCanGoTo.add(neighbor);
                    parents.put(neighbor, current);
                }
            }
        }
        return null;
    }
    public static  Pair<LinkedList<SystemsPos>, Double> returnAndPrint(SystemsPos start, SystemsPos end, SystemsPos gripPos){
        Pair<LinkedList<SystemsPos>, Double> A = getPath(start,end,gripPos);
        printPath(A.getFirst());
        return A;
    }
    private static boolean isInList(SystemsPos neighbor, LinkedList<SystemsPos> nodesCanGoTo) {
        return nodesCanGoTo.contains(neighbor);
    }

    public static void main(String[] args) {
        returnAndPrint(CONE_HIGH, LOW, GRIPER_TWO);
    }
}
