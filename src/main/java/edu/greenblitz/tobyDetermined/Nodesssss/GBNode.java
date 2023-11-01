package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.utils.GBCommand;

import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos;

import java.util.Collections;
import java.util.LinkedList;

public abstract class GBNode {
    protected boolean isNeighborsSet;
    protected LinkedList<SystemsPos> neighbors;

    protected GBCommand command;
    protected final LinkedList<SystemsPos> otherSystemMustBeToEnter;
    protected final LinkedList<SystemsPos> otherSystemMustBeToOut;


    public GBNode(GBCommand command) {
        neighbors = new LinkedList<>();
        isNeighborsSet = false;
        this.command = command;
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

    public void setOtherSystemMustBeToEnter(SystemsPos[] otherSystemMustBeToEnter){
        Collections.addAll(this.otherSystemMustBeToEnter, otherSystemMustBeToEnter);
    }
    public void setOtherSystemMustBeToOut(SystemsPos[] otherSystemMustBeToOut){
        Collections.addAll(this.otherSystemMustBeToOut, otherSystemMustBeToOut);
    }

    public GBCommand getCommand() {
        return command;
    }

    public LinkedList<SystemsPos> getNeighbors() {
        return neighbors;
    }

    public boolean getIsAtNode() {
        return false;
    }


}
