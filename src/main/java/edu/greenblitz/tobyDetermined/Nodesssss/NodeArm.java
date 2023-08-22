package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.utils.GBCommand;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;

public class NodeArm {
    private NodeArm parent;
    private GBCommand command;
    private int id;
    private LinkedList<NodeArm> neighbors;
    private double anglePos;
    private double extendPos;
    public NodeArm(GBCommand command, int id, double x, double y){
        this.id = id;
        extendPos = x;
        anglePos = y;
        this.command = command;
        neighbors = new LinkedList<>();
    }

    public NodeArm getParent(){
        return parent;
    }

    public void setParent(NodeArm parent) {
        this.parent = parent;
    }

    public double getExtendPos() {
        return extendPos;
    }
    public double getAnglePos() {
        return anglePos;
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
