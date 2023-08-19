package edu.greenblitz.tobyDetermined;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;

import java.util.LinkedList;

public class aStar {
    static LinkedList<NodeArm> open = new LinkedList<>();
    static LinkedList<NodeArm> closed = new LinkedList<>();
    static NodeBase nodeBase = new NodeBase();

   public static double getHGcost(NodeArm point, NodeArm current){
       double distance =  nodeBase.getDistanceBetweenToPoints(point,current);
       if(distance < 0){
           distance*=-1;
       }
       return distance;
   }
    public static double getFcost(NodeArm current, NodeArm start, NodeArm end){
       double gCost = getHGcost(start,current);
       double hCost = getHGcost(end,current);
       return gCost+hCost;
    }


    public static NodeArm getLowestFcost(LinkedList<NodeArm> open, NodeArm start, NodeArm end) {
       if (open.isEmpty()) {
           return null; // Handle the case where the list is empty
       }
       int saveI = 0;
       double fCost = getFcost(open.get(0), start, end);
       for (int i = 1; i < open.size(); i++) {
           double currentFCost = getFcost(open.get(i), start, end);
           if (currentFCost < fCost) {
               fCost = currentFCost;
               saveI = i;
           }
       }
       return open.get(saveI);
   }


        public static boolean isInList(NodeArm nodeArm, LinkedList<NodeArm> list){
       for(int i = 0; i<list.size(); i++){
           if(nodeArm.getId() == list.get(i).getId())
               return true;
       }
       return false;
    }
    public static void printPathPath(NodeArm nodeArm, NodeArm end){
       LinkedList<NodeArm>  pathList = new LinkedList<>();
       NodeArm current = nodeArm;
        while (current != null) {
            pathList.add(current);
            current = current.getParent();
        }

        for (int i = pathList.size() - 1; i >= 0; i--) {
            System.out.print(pathList.get(i).getId()+", ");
        }
        System.out.println();
    }
    public static void getPath(NodeArm start, NodeArm end){
        open.add(start);

        while (!open.isEmpty()) {
            NodeArm current = getLowestFcost(open, start, end);
            open.remove(current);
            closed.add(current);

            if (current.getId() == end.getId()) {
                printPathPath(current, end);
            }

            for (NodeArm neighbor : current.getNeighbors()) {
                if (!isInList(neighbor, closed)) {
                    if (!isInList(neighbor, open)) {
                        open.add(neighbor);
                        neighbor.setParent(current);
                    }
                }
            }
        }
    }
    public static void main(String args[]){
       getPath(nodeBase.getNode(6), nodeBase.getNode(7));
    }
}
