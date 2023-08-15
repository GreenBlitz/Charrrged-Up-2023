package edu.greenblitz.tobyDetermined;

import java.util.LinkedList;

public class NodeBase  {
    private NodeArm n1 ;
    private NodeArm n2 ;
    private NodeArm n3 ;
    private  NodeArm n4 ;
    private NodeArm n5 ;
    private NodeArm n6 ;
    private NodeArm n7 ;
    private NodeArm n8 ;
    private NodeArm n9 ;

    public NodeBase(){
        NodeArm n1 = new NodeArm(1);
        NodeArm n2 = new NodeArm(2);
        NodeArm n3 = new NodeArm(3);
        NodeArm n4 = new NodeArm(4);
        NodeArm n5 = new NodeArm(5);
        NodeArm n6 = new NodeArm(6);
        NodeArm n7 = new NodeArm(7);
        NodeArm n8 = new NodeArm(8);
        NodeArm n9 = new NodeArm(9);
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


    public NodeArm getNode(int num){
        if(num == n1.getNum())
            return n1;
        else if (num == n2.getNum())
            return n2;
        else if(num == n3.getNum())
            return n3;
        else if(num == n4.getNum())
            return n4;
        else if(num == n5.getNum())
            return n5;
        else if(num == n6.getNum())
            return n6;
        else if(num == n7.getNum())
            return n7;
        else if(num == n8.getNum())
            return n8;
        else return n9;
    }
    public int getNodeNumber(NodeArm nodeArm){return nodeArm.getNum();}

    public LinkedList<NodeArm> getnodeNeigbors(NodeArm nodeArm){return nodeArm.getNeighbors();
    }

}
