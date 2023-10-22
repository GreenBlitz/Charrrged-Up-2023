package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.math.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos;


public class AStar {

    public static <T> void printPath(LinkedList<T> pathList) {
        for (int i = 0; i <= pathList.size() - 1; i++) {
            System.out.print(pathList.get(i) + ", ");
        }
        System.out.println();
    }

    public static double getDistanceToStartPlusEnd(GriperPos current, GriperPos start, GriperPos end) {
        double gCost = NodeBase.getNode(current).getCost(start);
        double hCost = NodeBase.getNode(current).getCost(end);
        return gCost + hCost;

    }

    public static Pair<Double, GriperPos> addAnotherPathAndCost(GriperPos closed, GriperPos current, GriperPos griperPos, HashMap<GriperPos, LinkedList<GriperPos>> griperPath) {
        GriperPos holder = NodeBase.getGripPos(current);
        // System.out.println(current+", "+griperPos);
        if (!NodeBase.getNode(holder).getOtherSystemMustBe().isEmpty() && !NodeBase.getNode(holder).getOtherSystemMustBe().contains(griperPos)) {
            if (!NodeBase.getNode(closed).getOtherSystemMustBe().isEmpty() && !NodeBase.getNode(closed).getOtherSystemMustBe().contains(griperPos)) {
                double min = Double.MAX_VALUE;
                LinkedList<GriperPos> path = new LinkedList<>();
                for (int i = 0; i < NodeBase.getNode(holder).getOtherSystemMustBe().size(); i++) {
                    Pair<LinkedList<GriperPos>, Double> list = getPath(griperPos, NodeBase.getNode(holder).getOtherSystemMustBe().get(i), holder);
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

    public static GriperPos getLowestFcost(LinkedList<GriperPos> closed, LinkedList<GriperPos> open, GriperPos start, GriperPos end, GriperPos griperPos, HashMap<GriperPos, LinkedList<GriperPos>> griperPath) {
        if (open.isEmpty()) {
            return null; // Handle the case where the list is empty
        }
        int saveI = 0;
        if (open.contains(end)) {
            double fCost = getDistanceToStartPlusEnd(end, start, end);
            fCost += addAnotherPathAndCost(closed.get(0), end, griperPos, griperPath).getFirst();
            //cost += fCost;
            return end;
        }
        double fCost = getDistanceToStartPlusEnd(open.get(0), start, end);
        fCost += addAnotherPathAndCost(closed.get(0), open.get(0), griperPos, griperPath).getFirst();
        //null
        for (int i = 1; i < open.size(); i++) {
            double currentFCost = getDistanceToStartPlusEnd(open.get(i), start, end);
            Pair<Double, GriperPos> a = addAnotherPathAndCost(closed.get(0), open.get(i), griperPos, griperPath);
            currentFCost += a.getFirst();
            if (currentFCost < fCost) {
                fCost = currentFCost;
                saveI = i;

            }
        }
        //cost += fCost;
        return open.get(saveI);
    }

    public static Pair<LinkedList<GriperPos>, Double> returnPath(GriperPos nodeArm, Map<GriperPos, GriperPos> parents, HashMap<GriperPos, LinkedList<GriperPos>> griperPath, GriperPos end) {
        LinkedList<GriperPos> pathList = new LinkedList<>();
        GriperPos current = nodeArm;
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

    public static Pair<LinkedList<GriperPos>, Double> getPath(GriperPos start, GriperPos end, GriperPos gripPos) {
        LinkedList<GriperPos> nodesCanGoTo = new LinkedList<>();
        LinkedList<GriperPos> closed = new LinkedList<>();
        Map<GriperPos, GriperPos> parents = new HashMap<>();
        HashMap<GriperPos, LinkedList<GriperPos>> griperPath = new HashMap<>();
        double cost = 0;
        nodesCanGoTo.add(start);
        closed.add(start);
        while (!nodesCanGoTo.isEmpty()) {

            GriperPos current;
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

            for (GriperPos neighbor : NodeBase.getNode(current).getNeighbors()) {
                if (!isInList(neighbor, closed) && !isInList(neighbor, nodesCanGoTo)) {
                    nodesCanGoTo.add(neighbor);
                    parents.put(neighbor, current);
                }
            }
        }
        return null;
    }
    public static  Pair<LinkedList<GriperPos>, Double> returnAndPrint(GriperPos start, GriperPos end, GriperPos gripPos){
        Pair<LinkedList<GriperPos>, Double> A = getPath(start,end,gripPos);
        printPath(A.getFirst());
        return A;
    }
    private static boolean isInList(GriperPos neighbor, LinkedList<RobotMap.Intake.GriperPos> nodesCanGoTo) {
        return nodesCanGoTo.contains(neighbor);
    }

    public static void main(String[] args) {
        returnAndPrint(CONE_HIGH, LOW, GRIPER_TWO);
    }
}
