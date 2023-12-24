package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes;


import java.util.Collections;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.getAllSystemsState;

public abstract class GBNode {
    protected boolean isNeighborsSet;
    protected LinkedList<SystemsState> neighbors;
    protected LinkedList<SystemsState> system2MustBeToEnterState;
    protected LinkedList<SystemsState> system2MustBeToExitState;


    public GBNode() {
        neighbors = new LinkedList<>();
        isNeighborsSet = false;
        system2MustBeToEnterState = new LinkedList<>(getAllSystemsState());
        system2MustBeToExitState = new LinkedList<>(getAllSystemsState());
    }

    public void addNeighbors(SystemsState[] neighbors) {
        if (!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }

    public LinkedList<SystemsState> getSystem2MustBeToEnterState() {
        return system2MustBeToEnterState;
    }

    public LinkedList<SystemsState> getSystem2MustBeToExitState() {
        return system2MustBeToExitState;
    }

    public void setMustBeToEnter(SystemsState[] system2MustBeToEnter) {
        this.system2MustBeToEnterState = new LinkedList<>();
        Collections.addAll(this.system2MustBeToEnterState, system2MustBeToEnter);
    }

    public void setMustBeToOut(SystemsState[] system2MustBeToOut) {
        this.system2MustBeToExitState = new LinkedList<>();
        Collections.addAll(this.system2MustBeToExitState, system2MustBeToOut);
    }

    public LinkedList<SystemsState> getNeighbors() {
        return neighbors;
    }

}
