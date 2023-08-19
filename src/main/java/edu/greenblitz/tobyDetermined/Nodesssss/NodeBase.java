package edu.greenblitz.tobyDetermined.Nodesssss;

import java.util.LinkedList;

public class NodeBase  {
    private NodeArm A;
    private NodeArm B;
    private NodeArm C;
    private NodeArm D;
    private NodeArm E;
    private NodeArm F;
    private NodeArm S;

    private int i = 1;
    private LinkedList<NodeArm> list = new LinkedList<>();

    public int addToList(NodeArm nodeArm, LinkedList<NodeArm> list){
        list.add(i,nodeArm);
        i=i+1;
        return i;
    }
    public NodeBase(){
        list.add(0,null);
        i = addToList(A = new NodeArm(1, i, 1,5), list);
        i = addToList(B = new NodeArm(2, i, 2,6),list);
        i = addToList(C = new NodeArm(3, i, 3,8),list);
        i = addToList(D = new NodeArm(4, i, 9,4),list);
        i = addToList(E = new NodeArm(5, i, 11,8),list);
        i = addToList(F = new NodeArm(6, i, 15,3),list);
        i = addToList(S = new NodeArm(7, i, 12,12),list);
        A.setNeighbors(new NodeArm[] {B, C, E, D, F});
        B.setNeighbors(new NodeArm[] {A, D, E, C});
        C.setNeighbors(new NodeArm[] {A, B, D, E});
        D.setNeighbors(new NodeArm[] {A,B,C,E,S});
        E.setNeighbors(new NodeArm[] {A,B,D,C});
        F.setNeighbors(new NodeArm[] {A});
       S.setNeighbors(new NodeArm[] {D});
    }

    public LinkedList<NodeArm> getList(){
        return list;
    }

    public NodeArm getNode(int index) {
        for (int j = 1; j < list.size(); j++) {
            NodeArm node = list.get(j);
            if (index == node.getId()) {
                return node;
            }
        }
        return null;
    }

    public double getDistanceBetweenToPoints(NodeArm a, NodeArm b ){
        return Math.sqrt(Math.pow(a.getXpos()-b.getXpos(), a.getXpos()-b.getXpos())+Math.pow(a.getYpos()-b.getYpos(), a.getYpos()-b.getYpos()));
    }

}
