package edu.greenblitz.tobyDetermined.Nodesssss;

import java.util.Collections;
import java.util.LinkedList;

public class NodeArm {
    private NodeArm parent;
    private int id;
    private final LinkedList<NodeArm> neighbors;
    private final double anglePos;
    private final double extendPos;
    public NodeArm(int id, double extenderPos, double anglePos){
        this.id = id;
        this.extendPos = extenderPos;
        this.anglePos = anglePos;
        neighbors = new LinkedList<NodeArm>();
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
}
