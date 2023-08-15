package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;

import java.util.Collections;
import java.util.LinkedList;

public class NodeArm {
    //private GBCommand command;
    // private String name;
    private int index;
    private LinkedList<NodeArm> neighbors;

    private Double replaceCommand;


    public NodeArm(double command, int index){
        this.index = index;
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
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    //public void runCommand(){
        //command.schedule();
    //}

}
