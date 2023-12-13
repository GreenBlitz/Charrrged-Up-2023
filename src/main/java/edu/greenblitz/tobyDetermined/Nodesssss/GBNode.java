package edu.greenblitz.tobyDetermined.Nodesssss;


import java.util.Collections;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.CreateNodes.listSystemsPos;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;

public abstract class GBNode {
    protected boolean isNeighborsSet;
    protected LinkedList<SystemsPos> neighbors;
    protected LinkedList<SystemsPos> otherSystemMustBeToEnter1;
    protected LinkedList<SystemsPos> otherSystemMustBeToOut1;
    protected LinkedList<SystemsPos> otherSystemMustBeToEnter2;
    protected LinkedList<SystemsPos> otherSystemMustBeToOut2;


    public GBNode() {
        neighbors = new LinkedList<>();
        isNeighborsSet = false;
        otherSystemMustBeToEnter1 = listSystemsPos;
        otherSystemMustBeToOut1 = listSystemsPos;
        otherSystemMustBeToEnter2 = listSystemsPos;
        otherSystemMustBeToOut2 = listSystemsPos;
    }

    public void addNeighbors(SystemsPos[] neighbors) {
        if (!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
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

    public LinkedList<SystemsPos> getOtherSystemMustBeToEnter1() {
        return otherSystemMustBeToEnter1;
    }

    public LinkedList<SystemsPos> getOtherSystemMustBeToOut1() {
        return otherSystemMustBeToOut1;
    }

    public void setOtherSystemMustBeToEnter1(SystemsPos[] otherSystemMustBeToEnter1) {
        this.otherSystemMustBeToEnter1 = new LinkedList<>();
        Collections.addAll(this.otherSystemMustBeToEnter1, otherSystemMustBeToEnter1);
    }

    public void setOtherSystemMustBeToOut1(SystemsPos[] otherSystemMustBeToOut1) {
        this.otherSystemMustBeToOut1 = new LinkedList<>();
        Collections.addAll(this.otherSystemMustBeToOut1, otherSystemMustBeToOut1);
    }

    public LinkedList<SystemsPos> getNeighbors() {
        return neighbors;
    }

}
