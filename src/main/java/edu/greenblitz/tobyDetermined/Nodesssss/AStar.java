package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.LinkedList;

public class AStar {
    static LinkedList<NodeArm> open = new LinkedList<>();
    static LinkedList<NodeArm> closed = new LinkedList<>();
    static NodeBase nodeBase = NodeBase.getInstance();

  public static double getDistanceFromCurrentNodeToEndOrStartNode(NodeArm point, NodeArm current){
       return Math.abs(nodeBase.getDistanceBetweenToPoints(point,current));

  }
    public static double getDistanceToStartPlusEnd(NodeArm current, NodeArm start, NodeArm end){
       double gCost = getDistanceFromCurrentNodeToEndOrStartNode(start,current);
       double hCost = getDistanceFromCurrentNodeToEndOrStartNode(end,current);
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
      for (NodeArm arm : list) {
           if (nodeArm.getId() == arm.getId())
               return true;
       }
       return false;
    }

    public static LinkedList<NodeArm> returnPath(NodeArm nodeArm){
       LinkedList<NodeArm> pathList = new LinkedList<>();
       LinkedList<NodeArm> getPath = new LinkedList<>();
       NodeArm current = nodeArm;
       while (current != null) {
            pathList.add(current);
            current = current.getParent();
       }

       for (int i = pathList.size() - 1; i >= 0; i--) {
           getPath.add(pathList.get(i));

       }
       printPath(pathList);
       return getPath;
    }
    public static void printPath(LinkedList<NodeArm> pathList){
        String returningPath = "";
        for (int i = pathList.size() - 1; i >= 0; i--) {
            returningPath += pathList.get(i).getId()+", ";
            System.out.print(pathList.get(i).getId()+", ");
        }
        returningPath+="\n";
        SmartDashboard.putString("Returning Path",returningPath);
        System.out.println();
    }
    public static LinkedList<NodeArm> getPath(NodeArm start, NodeArm end){
        open.add(start);
        while (!open.isEmpty()) {
            NodeArm current = getLowestFcost(open, start, end);
            open.remove(current);
            closed.add(current);

            if (current.getId() == end.getId()) {
                return returnPath(current);
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
        return null;
    }
    public static void main(String[] args){
       getPath(nodeBase.getNode(7), nodeBase.getNode(1));
    }
}
