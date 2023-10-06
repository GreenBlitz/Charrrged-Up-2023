package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;

import java.util.Collections;
import java.util.LinkedList;

public class NodeArm {
    private int clawPos;//1 = cone catch . 2 = cube catch . 3 = release . 4590 = null
    private int id;
    private final LinkedList<NodeArm> neighbors;
    private final double anglePos;
    private final double extendPos;
    public NodeArm(int id, double extenderPos, double anglePos){
        this.id = id;
        this.extendPos = extenderPos;
        this.anglePos = anglePos;
        clawPos = 4590;
        neighbors = new LinkedList<NodeArm>();
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

    public void setClawPos(int clawPos) {
        this.clawPos = clawPos;
    }

    public int getClawPos() {
        return clawPos;
    }
}
