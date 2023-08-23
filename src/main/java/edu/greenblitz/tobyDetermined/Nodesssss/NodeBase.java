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
    private NodeArm currentNode;
    private int i = 1;
    private final LinkedList<NodeArm> list = new LinkedList<>();
    private static NodeBase instance;
    private final double tolA;//magic
    private final double tolL;//magic

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
        i = addToList(A = new NodeArm( i, 1,5), list);
        i = addToList(B = new NodeArm( i, 2,6),list);
        i = addToList(C = new NodeArm( i, 3,8),list);
        i = addToList(D = new NodeArm( i, 9,4),list);
        i = addToList(E = new NodeArm( i, 11,8),list);
        i = addToList(F = new NodeArm( i, 15,3),list);
        i = addToList(S = new NodeArm( i, 12,12),list);
        A.setNeighbors(new NodeArm[] {B, C, E, D, F});
        B.setNeighbors(new NodeArm[] {A, D, E, C});
        C.setNeighbors(new NodeArm[] {A, B, D, E});
        D.setNeighbors(new NodeArm[] {A,B,C,E,S});
        E.setNeighbors(new NodeArm[] {A,B,D,C});
        F.setNeighbors(new NodeArm[] {A});
        S.setNeighbors(new NodeArm[] {D});
        currentNode = A;
        tolA = 0.05; // magic
        tolL = 0.05; // magic
    }

    public NodeArm getCurrentNodeIndex() {
        return currentNode;
    }
    public void setCurrentNode(NodeArm nodeArm){
        currentNode = nodeArm;
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

    public boolean getIfInNode(double angle , double length,  NodeArm index){
        return (angle >= index.getAnglePos() - tolA && angle <= index.getAnglePos() + tolA)
                &&
                (length >= index.getExtendPos() - tolL && length <= index.getExtendPos() + tolL);
    }
    public double getDistanceBetweenToPoints(NodeArm a, NodeArm b ){
        return Math.sqrt(
                Math.pow(a.getAnglePos()-b.getAnglePos(), 2)
                +
                Math.pow(a.getExtendPos()-b.getExtendPos(), 2));
    }

}
