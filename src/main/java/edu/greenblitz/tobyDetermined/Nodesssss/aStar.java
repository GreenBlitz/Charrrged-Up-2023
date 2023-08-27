package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.LinkedList;

public class aStar {
    static LinkedList<NodeArm> open = new LinkedList<>();
    static LinkedList<NodeArm> closed = new LinkedList<>();
    static NodeBase nodeBase = NodeBase.getInstance();

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
       for (NodeArm arm : list) {
           if (nodeArm.getId() == arm.getId())
               return true;
       }
       return false;
    }

    public static LinkedList<NodeArm> printPathPath(NodeArm nodeArm){
       LinkedList<NodeArm>  pathList = new LinkedList<>();
       LinkedList<NodeArm> getPath = new LinkedList<>();
       NodeArm current = nodeArm;
       String returningPath = "";
        while (current != null) {
            pathList.add(current);
            current = current.getParent();
        }

        for (int i = pathList.size() - 1; i >= 0; i--) {
            returningPath += pathList.get(i).getId()+", ";
            getPath.add(pathList.get(i));
        }
        returningPath+="\n";
        SmartDashboard.putString("Returning Path",returningPath);
        return getPath;
    }
    public static LinkedList<NodeArm> getPath(NodeArm start, NodeArm end){
        open.add(start);

        while (!open.isEmpty()) {
            NodeArm current = getLowestFcost(open, start, end);
            open.remove(current);
            closed.add(current);

            assert current != null;
            if (current.getId() == end.getId()) {
                return printPathPath(current);
            }

            for (NodeArm neighbor : current.getNeighbors()) {
                if (isInList(neighbor, closed)) {
                    if (isInList(neighbor, open)) {
                        open.add(neighbor);
                        neighbor.setParent(current);
                    }
                }
            }
        }
        return null;
    }
    public static void main(String[] args){
       getPath(nodeBase.getNode(6), nodeBase.getNode(3));
    }
}
