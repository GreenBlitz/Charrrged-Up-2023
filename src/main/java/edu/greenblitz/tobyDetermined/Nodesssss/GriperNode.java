package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.GBCommand;
import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos;
import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem;
import java.util.Collections;
import java.util.LinkedList;

public class GriperNode extends GBNode {

    private final LinkedList<SystemsPos> armMustBe;
    private boolean isNeighborsSet;
    private final LinkedList<SystemsPos> neighbors;
    private final GBCommand command;

    public GriperNode(GBCommand command) {
        super(command);
        neighbors = new LinkedList<>();
        isNeighborsSet = false;
        this.command = command;
        armMustBe = new LinkedList<>();
    }

    public void addNeighbors(SystemsPos[] neighbors) {
        if (!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }

    @Override
    public LinkedList<SystemsPos> getOtherSystemMustBe() {
        return armMustBe;
    }

    @Override
    public void setOtherSystemMustBe(SystemsPos[] griperMustBe) {
        Collections.addAll(this.armMustBe, griperMustBe);
    }

    public GBCommand getCommand() {
        return command;
    }

    public LinkedList<SystemsPos> getNeighbors() {
        return neighbors;
    }


}
