package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.commands.NodeCommand;
import edu.greenblitz.utils.GBCommand;

import java.util.LinkedList;

public class NodeBase  {
    private NodeArm A;
    private NodeArm B;
    private NodeArm C;
    private NodeArm D;
    private NodeArm E;
    private NodeArm F;
    private NodeArm S;
    private NodeArm currentNode;
    private int i = 1;
    private LinkedList<NodeArm> list = new LinkedList<>();
    private static NodeBase instance;
    private double tol;//magic
    public static NodeBase getInstance() {
        init();
        return instance;
    }

    public static void init() {
        if (instance == null) {
            instance = new NodeBase();
        }
    }

    public int addToList(NodeArm nodeArm, LinkedList<NodeArm> list){
        list.add(i,nodeArm);
        i=i+1;
        return i;
    }
    public NodeBase(){
        list.add(0,null);
        i = addToList(A = new NodeArm(new NodeCommand(i), i, 1,5), list);
        i = addToList(B = new NodeArm(new NodeCommand(i), i, 2,6),list);
        i = addToList(C = new NodeArm(new NodeCommand(i), i, 3,8),list);
        i = addToList(D = new NodeArm(new NodeCommand(i), i, 9,4),list);
        i = addToList(E = new NodeArm(new NodeCommand(i), i, 11,8),list);
        i = addToList(F = new NodeArm(new NodeCommand(i), i, 15,3),list);
        i = addToList(S = new NodeArm(new NodeCommand(i), i, 12,12),list);
        A.setNeighbors(new NodeArm[] {B, C, E, D, F});
        B.setNeighbors(new NodeArm[] {A, D, E, C});
        C.setNeighbors(new NodeArm[] {A, B, D, E});
        D.setNeighbors(new NodeArm[] {A,B,C,E,S});
        E.setNeighbors(new NodeArm[] {A,B,D,C});
        F.setNeighbors(new NodeArm[] {A});
        S.setNeighbors(new NodeArm[] {D});
        currentNode = A;
        tol = 0.05; // magic
    }

    public NodeArm getCurrentNode() {
        return currentNode;
    }
    public void setCurrentNode(NodeArm nodeArm){
        currentNode = nodeArm;
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

    public boolean getIfInNode(double angle , int index){
        return angle >= getNode(index).getAnglePos() - tol || angle <= getNode(index).getAnglePos() + tol;
    }
    public double getDistanceBetweenToPoints(NodeArm a, NodeArm b ){
        return Math.sqrt(Math.pow(a.getAnglePos()-b.getAnglePos(), a.getAnglePos()-b.getAnglePos() + Math.pow(a.getExtendPos()-b.getExtendPos(), a.getExtendPos()-b.getExtendPos())));
    }

}
