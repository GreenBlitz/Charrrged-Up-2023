package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.tobyDetermined.RobotMap.Intake.SystemsPos;

import java.util.Collections;
import java.util.LinkedList;

public abstract class GBNode {
    private boolean isNeighborsSet;
    private final LinkedList<SystemsPos> neighbors;
    private GBCommand command;
    private LinkedList<SystemsPos> OtherSystemMustBe;

    public GBNode(GBCommand command) {
        neighbors = new LinkedList<>();
        isNeighborsSet = false;
        this.command = command;
        OtherSystemMustBe = new LinkedList<>();
    }

    public void addNeighbors(SystemsPos[] neighbors) {
        if (!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }

    public void setOtherSystemMustBe(SystemsPos[] griperMustBe) {
        Collections.addAll(this.OtherSystemMustBe, griperMustBe);
    }

    public LinkedList<SystemsPos> getOtherSystemMustBe() {
        return OtherSystemMustBe;
    }

    public GBCommand getCommand() {
        return command;
    }

    public LinkedList<SystemsPos> getNeighbors() {
        return neighbors;
    }

    public boolean getIfInNode() {
        return false;
    }


}
