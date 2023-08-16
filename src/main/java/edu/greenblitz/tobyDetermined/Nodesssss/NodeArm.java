package edu.greenblitz.tobyDetermined.Nodesssss;

import java.util.Collections;
import java.util.LinkedList;

public class NodeArm {
    //private GBCommand command;
    private int id;
    private LinkedList<NodeArm> neighbors;

    private Double replaceCommand;


    public NodeArm(double command, int id){
        this.id = id;
        replaceCommand = command;
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //public void runCommand(){
        //command.schedule();
    //}

}
