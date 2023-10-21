package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.GBCommand;

import java.util.Collections;
import java.util.LinkedList;

public abstract class GBNode {
    private boolean isNeighborsSet;
    private final LinkedList<RobotMap.Intake.GriperPos> neighbors;
    private GBCommand command;

    public GBNode(GBCommand command ){
        neighbors = new LinkedList<RobotMap.Intake.GriperPos>();
        isNeighborsSet = false;
        this.command = command;
    }
    public void addNeighbors(RobotMap.Intake.GriperPos[] neighbors) {
        if(!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }

    public GBCommand getCommand() {
        return command;
    }

    public LinkedList<RobotMap.Intake.GriperPos> getNeighbors(){
        return neighbors;
    }

}
