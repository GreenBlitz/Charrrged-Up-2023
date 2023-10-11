package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.PresetPositions.*;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.PresetPositions;

public class AStar {

    public static double getDistanceToStartPlusEnd(PresetPositions current, NodeArm start, NodeArm end){
       double gCost = Math.abs(NodeBase.getInstance().getDistanceBetweenTwoPoints(start,NodeBase.getInstance().getNode(current)));
       double hCost = Math.abs(NodeBase.getInstance().getDistanceBetweenTwoPoints(end,NodeBase.getInstance().getNode(current)));
       return gCost+hCost;
    }


    public static PresetPositions getLowestFcost(LinkedList<PresetPositions> open, NodeArm start, NodeArm end) {
       if (open.isEmpty()) {
           return null; // Handle the case where the list is empty
       }
       int saveI = 0;
       double fCost = getDistanceToStartPlusEnd(open.get(0), start, end);
       for (int i = 1; i < open.size(); i++) {
           double currentFCost = getDistanceToStartPlusEnd(open.get(i), start, end);
           if (currentFCost < fCost) {
               fCost = currentFCost;
               saveI = i;
           }
       }
       return open.get(saveI);
   }


   public static boolean isInList(PresetPositions nodeArmPos, LinkedList<PresetPositions> list){
       return list.contains(nodeArmPos);
   }

    public static LinkedList<PresetPositions> returnPath(PresetPositions nodeArm,Map<PresetPositions, PresetPositions> parents){
       LinkedList<PresetPositions> pathList = new LinkedList<>();
       PresetPositions current = nodeArm;
       while (current != null) {
            pathList.addFirst(current);
            current = parents.get(current);
       }
       printPath(pathList);
       return pathList;
    }
    public static void printPath(LinkedList<PresetPositions> pathList){
        for (int i = 0; i <= pathList.size() - 1; i++) {
            System.out.print(pathList.get(i)+", ");
        }
        System.out.println();
    }
    public static LinkedList<PresetPositions> getPath(PresetPositions start, PresetPositions end){
        LinkedList<PresetPositions> nodesCanGoTo = new LinkedList<>();
        LinkedList<PresetPositions> closed = new LinkedList<>();
        Map<PresetPositions, PresetPositions> parents = new HashMap<>();
        nodesCanGoTo.add(start);

        while (!nodesCanGoTo.isEmpty()) {
            PresetPositions current = getLowestFcost(nodesCanGoTo, NodeBase.getInstance().getNode(start), NodeBase.getInstance().getNode(end));
            nodesCanGoTo.remove(current);
            closed.add(current);

            if (current.equals(end)) {
                return returnPath(current, parents);
            }

            for (PresetPositions neighbor : NodeBase.getInstance().getNode(current).getNeighbors()) {
                if (!isInList(neighbor, closed) && !isInList(neighbor, nodesCanGoTo)) {
                    nodesCanGoTo.add(neighbor);
                    parents.put(neighbor, current);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        getPath(LOW,CONE_HIGH);
    }
}
