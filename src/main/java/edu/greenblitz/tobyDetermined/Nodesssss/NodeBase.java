package edu.greenblitz.tobyDetermined.Nodesssss;

import java.util.LinkedList;

public class NodeBase  {
    private NodeArm n1;
    private NodeArm n2;
    private NodeArm n3;
    private NodeArm n4;
    private NodeArm n5;
    private NodeArm n6;
    private NodeArm n7;
    private NodeArm n8;
    private NodeArm n9;
    private int i = 0;
    private LinkedList<NodeArm> list = new LinkedList<>();

    public int addToList(NodeArm nodeArm, LinkedList<NodeArm> list){
        list.add(i,nodeArm);
        i=i++;
        return i;
    }

    public NodeBase(){

        i = addToList(n1 = new NodeArm(1, 1), list);
        i = addToList(n2 = new NodeArm(2, 2),list);
        i = addToList(n3 = new NodeArm(3, 3),list);
        i = addToList(n4 = new NodeArm(4, 4),list);
        i = addToList(n5 = new NodeArm(5, 5),list);
        i = addToList(n6 = new NodeArm(6, 6),list);
        i = addToList(n7 = new NodeArm(7, 7),list);
        i = addToList(n8 = new NodeArm(8, 8),list);
        i = addToList(n9 = new NodeArm(9, 9),list);
        n1.setNeighbors(new NodeArm[] {n2, n3});
        n2.setNeighbors(new NodeArm[] {n1, n4,n5,n6});
        n3.setNeighbors(new NodeArm[] {n1, n7,n8,n9});
        n4.setNeighbors(new NodeArm[] {n2});
        n5.setNeighbors(new NodeArm[] {n2});
        n6.setNeighbors(new NodeArm[] {n2});
        n7.setNeighbors(new NodeArm[] {n3});
        n8.setNeighbors(new NodeArm[] {n3});
        n9.setNeighbors(new NodeArm[] {n3});
    }

    public LinkedList<NodeArm> getList(){
        return list;
    }

    public NodeArm getNode(int index){
        if(index == n1.getIndex())
            return n1;
        else if (index == n2.getIndex())
            return n2;
        else if(index == n3.getIndex())
            return n3;
        else if(index == n4.getIndex())
            return n4;
        else if(index == n5.getIndex())
            return n5;
        else if(index == n6.getIndex())
            return n6;
        else if(index == n7.getIndex())
            return n7;
        else if(index == n8.getIndex())
            return n8;
        else return n9;
    }


}
