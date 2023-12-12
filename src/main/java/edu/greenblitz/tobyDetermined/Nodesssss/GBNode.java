package edu.greenblitz.tobyDetermined.Nodesssss;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemConstants.*;
import java.util.Collections;
import java.util.LinkedList;

public abstract class GBNode {
    protected boolean isNeighborsSet;
    protected LinkedList<SystemsPos> neighbors;

    protected final LinkedList<SystemsPos> otherSystemMustBeToEnter;
    protected final LinkedList<SystemsPos> otherSystemMustBeToOut;


    public GBNode() {
        neighbors = new LinkedList<>();
        isNeighborsSet = false;
        otherSystemMustBeToEnter = new LinkedList<>();
        otherSystemMustBeToOut = new LinkedList<>();
    }

    public void addNeighbors(SystemsPos[] neighbors) {
        if (!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }

    public LinkedList<SystemsPos> getOtherSystemMustBeToEnter() {
        return otherSystemMustBeToEnter;
    }

    public LinkedList<SystemsPos> getOtherSystemMustBeToOut() {
        return otherSystemMustBeToOut;
    }

    public void setOtherSystemMustBeToEnter(SystemsPos[] otherSystemMustBeToEnter) {
        Collections.addAll(this.otherSystemMustBeToEnter, otherSystemMustBeToEnter);
    }

    public void setOtherSystemMustBeToOut(SystemsPos[] otherSystemMustBeToOut) {
        Collections.addAll(this.otherSystemMustBeToOut, otherSystemMustBeToOut);
    }

    public LinkedList<SystemsPos> getNeighbors() {
        return neighbors;
    }

}
