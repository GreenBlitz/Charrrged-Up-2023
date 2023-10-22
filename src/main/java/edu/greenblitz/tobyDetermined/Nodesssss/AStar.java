package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.wpi.first.math.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos;
//import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.PresetPositions.*;
//import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.PresetPositions;

public class AStar {
    public static <T> void printPath(LinkedList<T> pathList) {
        for (int i = 0; i <= pathList.size() - 1; i++) {
            System.out.print(pathList.get(i) + ", ");
        }
        System.out.println();
    }



    public static <T extends GBNode<T>> double getDistanceToStartPlusEnd(T current, T start, T end) {
        double gCost = current.getCost(start);
        double hCost = current.getCost(end);
        return gCost + hCost ;
    }
    public static <T> double addAnotherPathAndCost(T current, T griperPos, HashMap<T, LinkedList<String>> griperPath){
        if(!NodeBase.getNode((GriperPos) current).getGriperMustBe().isEmpty() && !NodeBase.getNode((GriperPos) current).getGriperMustBe().contains(griperPos)) {
                double min = Double.MAX_VALUE;
                LinkedList<String> path = new LinkedList<>();
                for (int i = 0; i < NodeBase.getNode((GriperPos) current).getGriperMustBe().size() - 1; i++) {
                    Pair<LinkedList<String>, Double> list = getPath(griperPos, NodeBase.getNode((GriperPos) current).getGriperMustBe().get(i), current);
                    if (min > list.getSecond()) {
                        min = list.getSecond();
                        path = list.getFirst();
                    }
                }
                    griperPath.put(current,  path);
                return min;
            }
        return 0;
    }
    public static <T> T getLowestFcost(
            LinkedList<T> open,
            T start,
            T end,
            T griperPos,
            double cost,
            HashMap<T, LinkedList<String>> griperPath
    ) {
        if (open.isEmpty()) {
            return null; // Handle the case where the list is empty
        }
        int saveI = 0;
        double fCost = getDistanceToStartPlusEnd(NodeBase.getNode((GriperPos) open.get(0)), NodeBase.getNode((GriperPos) start), NodeBase.getNode((GriperPos) end));
        for (int i = 1; i < open.size(); i++) {
            double currentFCost = getDistanceToStartPlusEnd(NodeBase.getNode((GriperPos) open.get(i)),NodeBase.getNode((GriperPos) start) , NodeBase.getNode((GriperPos) end));
            if (currentFCost < fCost) {
                fCost = currentFCost;
                saveI = i;
            }
        }
        cost+=fCost;
        return open.get(saveI);
    }

    public static <T> Pair<LinkedList<String>, Double> returnPath(T nodeArm, Map<T, T> parents,double cost, HashMap<T, LinkedList<String>> griperPath) {
        LinkedList<String> pathList = new LinkedList<>();
        T current = nodeArm;
        while (current != null) {
            pathList.addFirst(current.toString());
            if (griperPath.containsKey(current)) {
                for (int i = 0; i < griperPath.get(current).size() - 1; i++) {
                    pathList.add(griperPath.get(current).get(i));
                }
            }
            current = parents.get(current);
        }
        printPath(pathList);
        return new Pair<>(pathList, cost);
    }

    public static <T> Pair<LinkedList<String>, Double> getPath(T start, T end, T gripPos) {
        LinkedList<T> nodesCanGoTo = new LinkedList<>();
        LinkedList<T> closed = new LinkedList<>();
        Map<T, T> parents = new HashMap<>();
        HashMap<T, LinkedList<String>> griperPath = new HashMap<>();
        double cost = 0;
        nodesCanGoTo.add(start);

        while (!nodesCanGoTo.isEmpty()) {
            T current = getLowestFcost(nodesCanGoTo, start, end, gripPos, cost, griperPath);
            nodesCanGoTo.remove(current);
            closed.add(current);

            if (current.equals(end)) {
                return returnPath(current, parents, cost, griperPath);
            }

            for (T neighbor : NodeBase.getNode((GriperPos) current).getNeighbors()) {
                if (!isInList(neighbor, closed) && !isInList(neighbor, nodesCanGoTo)) {
                    nodesCanGoTo.add(neighbor);
                    parents.put(neighbor, current);
                }
            }
        }
        return null;
    }

    private static <T> boolean isInList(T neighbor, LinkedList<T> nodesCanGoTo) {
        return nodesCanGoTo.contains(neighbor);
    }
    public static void main(String[] args) {
        getPath(LOW, CONE_HIGH, TWO);
    }
}
