package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.GBCommand;

import java.util.Collections;
import java.util.LinkedList;

public abstract class GBNode <T> {
    private boolean isNeighborsSet;
    private final LinkedList<T> neighbors;
    private GBCommand command;

    public GBNode(GBCommand command ){
        neighbors = new LinkedList<T>();
        isNeighborsSet = false;
        this.command = command;
    }
    public void addNeighbors(T[] neighbors) {
        if(!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }

    public GBCommand getCommand() {
        return command;
    }

    public LinkedList<T> getNeighbors(){
        return neighbors;
    }

}
