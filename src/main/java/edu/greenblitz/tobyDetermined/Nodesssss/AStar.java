package edu.greenblitz.tobyDetermined.Nodesssss;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AStar {

    public static double getDistanceToStartPlusEnd(NodeArm current, NodeArm start, NodeArm end){
       double gCost = Math.abs(NodeBase.getInstance().getDistanceBetweenTwoPoints(start,current));
       double hCost = Math.abs(NodeBase.getInstance().getDistanceBetweenTwoPoints(end,current));
       return gCost+hCost;
    }


    public static NodeArm getLowestFcost(LinkedList<NodeArm> open, NodeArm start, NodeArm end) {
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


   public static boolean isInList(NodeArm nodeArm, LinkedList<NodeArm> list){
       return list.contains(nodeArm);
   }

    public static LinkedList<NodeArm> returnPath(NodeArm nodeArm,Map<NodeArm, NodeArm> parents){
       LinkedList<NodeArm> pathList = new LinkedList<>();
       NodeArm current = nodeArm;
       while (current != null) {
            pathList.addFirst(current);
            current = parents.get(current);
       }
       printPath(pathList);
       return pathList;
    }
    public static void printPath(LinkedList<NodeArm> pathList){
        for (int i = 0; i <= pathList.size() - 1; i++) {
            System.out.print(pathList.get(i).getId()+", ");
        }
        System.out.println();
    }
    public static LinkedList<NodeArm> getPath(NodeArm start, NodeArm end){
        LinkedList<NodeArm> nodesCanGoTo = new LinkedList<NodeArm>();
        LinkedList<NodeArm> closed = new LinkedList<NodeArm>();
        Map<NodeArm, NodeArm> parents = new HashMap<>();
        nodesCanGoTo.add(start);

        while (!nodesCanGoTo.isEmpty()) {
            NodeArm current = getLowestFcost(nodesCanGoTo, start, end);
            nodesCanGoTo.remove(current);
            closed.add(current);

            if (current.getId() == end.getId()) {
                return returnPath(current, parents);
            }

            for (NodeArm neighbor : current.getNeighbors()) {
                if (!isInList(neighbor, closed) && !isInList(neighbor, nodesCanGoTo)) {
                    nodesCanGoTo.add(neighbor);
                    parents.put(neighbor, current);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        getPath(NodeBase.getInstance().getNode(NodeBase.SpecificNode.LOW),NodeBase.getInstance().getNode(NodeBase.SpecificNode.CONE_HIGH));
    }
}
