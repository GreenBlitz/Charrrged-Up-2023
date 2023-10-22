package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.GBCommand;

import java.util.Collections;
import java.util.LinkedList;

public abstract class GBNode <T,S> {
    private boolean isNeighborsSet;
    private final LinkedList<T> neighbors;
    private GBCommand command;

    private LinkedList<S> somethingMustBe;
    public GBNode(GBCommand command ){
        neighbors = new LinkedList<T>();
        isNeighborsSet = false;
        this.command = command;
        somethingMustBe = new LinkedList<>();
    }
    public void addNeighbors(T[] neighbors) {
        if(!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }
    public void setGriperMustBe(S[] griperMustBe) {
        Collections.addAll(this.somethingMustBe, griperMustBe);
    }

    public LinkedList<S> getGriperMustBe() {
        return somethingMustBe;
    }
    public GBCommand getCommand() {
        return command;
    }

    public LinkedList<T> getNeighbors(){
        return neighbors;
    }
    public double getCost(){return 1.0;}


    public abstract double getCost(T nodeArm);

}
