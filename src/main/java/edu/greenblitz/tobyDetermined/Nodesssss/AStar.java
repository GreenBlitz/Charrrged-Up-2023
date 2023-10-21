package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.wpi.first.math.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos;

public class AStar {
//    public static boolean checks(GriperPos cur, GriperPos griperPos){
//        if(cur.toString().contains("GRIPER")){
//            return !NodeBase.getNode(cur).getGriperMustBe().isEmpty() && !NodeBase.getNode(cur).getGriperMustBe().contains(griperPos);
//        }
//        else
//            return !NodeBase.getNode(cur).getGriperMustBe().isEmpty() && !NodeBase.getNode(cur).getGriperMustBe().contains(griperPos);
//    }
    public static double getDistanceToStartPlusEnd(GriperPos current, NodeArm start, NodeArm end, GriperPos griperPos, HashMap<GriperPos, LinkedList<GriperPos>> griperPath) {
//        double gCost = Math.abs(NodeBase.getDistanceBetweenTwoPoints(start, NodeBase.getNode(current)));
//        double hCost = Math.abs(NodeBase.getDistanceBetweenTwoPoints(end, NodeBase.getNode(current)));
        double gCost = NodeBase.getCost();
        double hCost = NodeBase.getCost();
        System.out.println("yyyyyyyyyyyyyyyyyyyyyyy");
        if(!NodeBase.getNode(current).getGriperMustBe().isEmpty()) {
            System.out.println("bllalalalalalal");
            if (!NodeBase.getNode(current).getGriperMustBe().contains(griperPos)) {
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
                return gCost + hCost + min;
            }
        }
        return gCost + hCost ;
    }


    public static GriperPos getLowestFcost(
            LinkedList<GriperPos> open,
            NodeArm start,
            NodeArm end,
            GriperPos griperPos,
            double cost,
            HashMap<GriperPos, LinkedList<GriperPos>> griperPath
    ) {
        if (open.isEmpty()) {
            return null; // Handle the case where the list is empty
        }
        int saveI = 0;
        double fCost = getDistanceToStartPlusEnd(open.get(0), start, end, griperPos, griperPath);
        for (int i = 1; i < open.size(); i++) {
            double currentFCost = getDistanceToStartPlusEnd(open.get(i), start, end, griperPos, griperPath);
            if (currentFCost < fCost) {
                fCost = currentFCost;
                saveI = i;
            }
        }
        cost+=fCost;
        return open.get(saveI);
    }


    public static boolean isInList(GriperPos nodeArmPos, LinkedList<GriperPos> list) {
        return list.contains(nodeArmPos);
    }

    public static Pair<LinkedList<GriperPos>, Double> returnPath(GriperPos nodeArm, Map<GriperPos, GriperPos> parents,double cost, HashMap<GriperPos, LinkedList<GriperPos>> griperPath) {
        LinkedList<GriperPos> pathList = new LinkedList<>();
        GriperPos current = nodeArm;
        while (current != null) {
            pathList.addFirst(current);
            if (griperPath.containsKey(current))
                    pathList.addAll(0,griperPath.get(current));
            current = parents.get(current);
        }
        printPath(pathList);
        return new Pair<>(pathList, cost);
    }

    public static void printPath(LinkedList<GriperPos> pathList) {
        for (int i = 0; i <= pathList.size() - 1; i++) {
            System.out.print(pathList.get(i) + ", ");
        }
        System.out.println();
    }

    public static Pair<LinkedList<GriperPos>, Double> getPath(GriperPos start, GriperPos end, GriperPos gripPos) {
        LinkedList<GriperPos> nodesCanGoTo = new LinkedList<>();
        LinkedList<GriperPos> closed = new LinkedList<>();
        Map<GriperPos, GriperPos> parents = new HashMap<>();
        HashMap<GriperPos, LinkedList<GriperPos>> griperPath = new HashMap<>();
        double cost = 0;
        nodesCanGoTo.add(start);

        while (!nodesCanGoTo.isEmpty()) {
            GriperPos current = getLowestFcost(nodesCanGoTo, NodeBase.getNode(start), NodeBase.getNode(end), gripPos, cost, griperPath);
            nodesCanGoTo.remove(current);
            closed.add(current);

            if (current.equals(end)) {
                return returnPath(current, parents, cost, griperPath);
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

    public static void main(String[] args) {
        getPath(LOW, CONE_HIGH, TWO);
    }
}
