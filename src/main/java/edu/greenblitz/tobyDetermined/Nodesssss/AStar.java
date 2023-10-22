package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.wpi.first.math.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.PresetPositions.*;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.PresetPositions;

public class AStar {
    public static <T> void printPath(LinkedList<T> pathList) {
        for (int i = 0; i <= pathList.size() - 1; i++) {
            System.out.print(pathList.get(i) + ", ");
        }
        System.out.println();
    }

    public static <T> boolean isInList(T nodeArmPos, LinkedList<T> list) {
        return list.contains(nodeArmPos);
    }

    public static <T extends GBNode<T>> double getDistanceToStartPlusEnd(T current, T start, T end) {
        double gCost = current.getCost(start);
        double hCost = current.getCost(end);
        return gCost + hCost ;
    }
    public static double addAnotherPathAndCost(PresetPositions current, GriperPos griperPos, HashMap<PresetPositions, LinkedList<GriperPos>> griperPath){
        if(!NodeBase.getNode(current).getGriperMustBe().isEmpty() && !NodeBase.getNode(current).getGriperMustBe().contains(griperPos)) {
                double min = Double.MAX_VALUE;
                LinkedList<GriperPos> path = new LinkedList<>();
                for (int i = 0; i < NodeBase.getNode(current).getGriperMustBe().size() - 1; i++) {
                    Pair<LinkedList<GriperPos>, Double> list = getPath(griperPos, NodeBase.getNode(current).getGriperMustBe().get(i), current);
                    if (min > list.getSecond()) {
                        min = list.getSecond();
                        path = list.getFirst();
                    }
                }
                griperPath.put(current, path);
                return min;
            }
        return 0;
    }
    public static PresetPositions getLowestFcost(
            LinkedList<PresetPositions> open,
            PresetPositions start,
            PresetPositions end,
            GriperPos griperPos,
            double cost,
            HashMap<PresetPositions, LinkedList<GriperPos>> griperPath
    ) {
        if (open.isEmpty()) {
            return null; // Handle the case where the list is empty
        }
        int saveI = 0;
        double fCost = getDistanceToStartPlusEnd(NodeBase.getNode(open.get(0)), NodeBase.getNode(start), NodeBase.getNode(end));
        for (int i = 1; i < open.size(); i++) {
            double currentFCost = getDistanceToStartPlusEnd(open.get(i), start, end);
            if (currentFCost < fCost) {
                fCost = currentFCost;
                saveI = i;
            }
        }
        cost+=fCost;
        return open.get(saveI);
    }

    public static Pair<LinkedList<String>, Double> returnPath(PresetPositions nodeArm, Map<PresetPositions, PresetPositions> parents,double cost, HashMap<PresetPositions, LinkedList<GriperPos>> griperPath) {
        LinkedList<String> pathList = new LinkedList<>();
        PresetPositions current = nodeArm;
        while (current != null) {
            pathList.addFirst(current.toString());
            if (griperPath.containsKey(current)) {
                for (int i = 0; i < griperPath.get(current).size() - 1; i++) {
                    pathList.add(griperPath.get(current).get(i).toString());
                }
            }
            current = parents.get(current);
        }
        printPath(pathList);
        return new Pair<>(pathList, cost);
    }

    public static Pair<LinkedList<String>, Double> getPath(PresetPositions start, PresetPositions end, GriperPos gripPos) {
        LinkedList<PresetPositions> nodesCanGoTo = new LinkedList<>();
        LinkedList<PresetPositions> closed = new LinkedList<>();
        Map<PresetPositions, PresetPositions> parents = new HashMap<>();
        HashMap<PresetPositions, LinkedList<GriperPos>> griperPath = new HashMap<>();
        double cost = 0;
        nodesCanGoTo.add(start);

        while (!nodesCanGoTo.isEmpty()) {
            PresetPositions current = getLowestFcost(nodesCanGoTo, start, end, gripPos, cost, griperPath);
            nodesCanGoTo.remove(current);
            closed.add(current);

            if (current.equals(end)) {
                return returnPath(current, parents, cost, griperPath);
            }

            for (PresetPositions neighbor : NodeBase.getNode(current).getNeighbors()) {
                if (!isInList(neighbor, closed) && !isInList(neighbor, nodesCanGoTo)) {
                    nodesCanGoTo.add(neighbor);
                    parents.put(neighbor, current);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        getPath(LOW, CONE_HIGH, TWO);
    }
}
