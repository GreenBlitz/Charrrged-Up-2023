package edu.greenblitz.tobyDetermined;

import java.util.Collections;
import java.util.LinkedList;

public class NodeArm {
    //private GBCommand command;
    // private String name;
    private int num;
    private LinkedList<NodeArm> neighbors;

    public NodeArm(int command){
        //this.command = command;
        num = command;
        neighbors = new LinkedList<>();
    }

    public void setNeighbors(NodeArm[] neighbors) {
        Collections.addAll(this.neighbors, neighbors);
    }

    public void addNeighbors(NodeArm neighbor){
        this.neighbors.add(neighbor);
    }

    public LinkedList<NodeArm> getNeighbors(){
        return neighbors;
    }
    public int getNum() {
        return num;
    }
    //public void runCommand(){
        //command.schedule();
    //}

}
