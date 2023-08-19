package edu.greenblitz.tobyDetermined.Nodesssss;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;

public class NodeArm {
    private NodeArm parent;
    //private GBCommand command;
    private int id;
    private LinkedList<NodeArm> neighbors;
    private double replaceCommand;
    private double Xpos;
    private double Ypos;
    public NodeArm(double command, int id, double x, double y){
        this.id = id;
        Xpos = x;
        Ypos = y;
        replaceCommand = command;
        neighbors = new LinkedList<>();
    }

    public NodeArm getParent(){
        return parent;
    }

    public void setParent(NodeArm parent) {
        this.parent = parent;
    }

    public double getXpos() {
        return Xpos;
    }
    public double getYpos() {
        return Ypos;
    }

    public void setNeighbors(NodeArm[] neighbors) {
        Collections.addAll(this.neighbors, neighbors);
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
