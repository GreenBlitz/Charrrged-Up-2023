package edu.greenblitz.tobyDetermined.Nodesssss;


import java.util.Collections;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.CreateNodes.listSystemsPos;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;

public abstract class GBNode {
    protected boolean isNeighborsSet;
    protected LinkedList<SystemsPos> neighbors;
    protected LinkedList<SystemsPos> otherSystemMustBeToEnter2;
    protected LinkedList<SystemsPos> otherSystemMustBeToOut2;
    protected LinkedList<SystemsPos> otherSystemMustBeToEnter3;
    protected LinkedList<SystemsPos> otherSystemMustBeToOut3;


    public GBNode() {
        neighbors = new LinkedList<>();
        isNeighborsSet = false;
        otherSystemMustBeToEnter2 = listSystemsPos;
        otherSystemMustBeToOut2 = listSystemsPos;
        otherSystemMustBeToEnter3 = listSystemsPos;
        otherSystemMustBeToOut3 = listSystemsPos;
    }

    public void addNeighbors(SystemsPos[] neighbors) {
        if (!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }

    public LinkedList<SystemsPos> getOtherSystemMustBeToEnter3() {
        return otherSystemMustBeToEnter3;
    }

    public LinkedList<SystemsPos> getOtherSystemMustBeToOut3() {
        return otherSystemMustBeToOut3;
    }

    public void setOtherSystemMustBeToEnter3(SystemsPos[] otherSystemMustBeToEnter3) {
        this.otherSystemMustBeToEnter3 = new LinkedList<>();
        Collections.addAll(this.otherSystemMustBeToEnter3, otherSystemMustBeToEnter3);
    }

    public void setOtherSystemMustBeToOut3(SystemsPos[] otherSystemMustBeToOut3) {
        this.otherSystemMustBeToOut3 = new LinkedList<>();
        Collections.addAll(this.otherSystemMustBeToOut3, otherSystemMustBeToOut3);
    }

    public LinkedList<SystemsPos> getOtherSystemMustBeToEnter2() {
        return otherSystemMustBeToEnter2;
    }

    public LinkedList<SystemsPos> getOtherSystemMustBeToOut2() {
        return otherSystemMustBeToOut2;
    }

    public void setOtherSystemMustBeToEnter2(SystemsPos[] otherSystemMustBeToEnter2) {
        this.otherSystemMustBeToEnter2 = new LinkedList<>();
        Collections.addAll(this.otherSystemMustBeToEnter2, otherSystemMustBeToEnter2);
    }

    public void setOtherSystemMustBeToOut2(SystemsPos[] otherSystemMustBeToOut2) {
        this.otherSystemMustBeToOut2 = new LinkedList<>();
        Collections.addAll(this.otherSystemMustBeToOut2, otherSystemMustBeToOut2);
    }

    public LinkedList<SystemsPos> getNeighbors() {
        return neighbors;
    }

}
