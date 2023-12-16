package edu.greenblitz.tobyDetermined.Nodesssss;


import java.util.Collections;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPosition;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.getAllSystemsPositions;

public abstract class GBNode {
    protected boolean isNeighborsSet;
    protected LinkedList<SystemsPosition> neighbors;
    protected LinkedList<SystemsPosition> system2MustBeToEnter;
    protected LinkedList<SystemsPosition> system2MustBeToOut;


    public GBNode() {
        neighbors = new LinkedList<>();
        isNeighborsSet = false;
        system2MustBeToEnter = new LinkedList<>(getAllSystemsPositions());
        system2MustBeToOut = new LinkedList<>(getAllSystemsPositions());
    }

    public void addNeighbors(SystemsPosition[] neighbors) {
        if (!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }

    public LinkedList<SystemsPosition> getSystem2MustBeToEnter() {
        return system2MustBeToEnter;
    }

    public LinkedList<SystemsPosition> getSystem2MustBeToOut() {
        return system2MustBeToOut;
    }

    public void setSystem2MustBeToEnter(SystemsPosition[] system2MustBeToEnter) {
        this.system2MustBeToEnter = new LinkedList<>();
        Collections.addAll(this.system2MustBeToEnter, system2MustBeToEnter);
    }

    public void setSystem2MustBeToOut(SystemsPosition[] system2MustBeToOut) {
        this.system2MustBeToOut = new LinkedList<>();
        Collections.addAll(this.system2MustBeToOut, system2MustBeToOut);
    }

    public LinkedList<SystemsPosition> getNeighbors() {
        return neighbors;
    }

}
