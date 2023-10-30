package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.GBCommand;

import java.util.Collections;
import java.util.LinkedList;

public class GriperNode extends GBNode {

    private LinkedList<RobotMap.Intake.SystemsPos> armMustBe;
    private boolean isNeighborsSet;
    private final LinkedList<RobotMap.Intake.SystemsPos> neighbors;
    private GBCommand command;

    public GriperNode(GBCommand command) {
        super(command);
        neighbors = new LinkedList<>();
        isNeighborsSet = false;
        this.command = command;
        armMustBe = new LinkedList<>();
    }

    public void addNeighbors(RobotMap.Intake.SystemsPos[] neighbors) {
        if (!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }

    @Override
    public LinkedList<RobotMap.Intake.SystemsPos> getOtherSystemMustBe() {
        return armMustBe;
    }

    @Override
    public void setOtherSystemMustBe(RobotMap.Intake.SystemsPos[] griperMustBe) {
        Collections.addAll(this.armMustBe, griperMustBe);
    }

    public GBCommand getCommand() {
        return command;
    }

    public LinkedList<RobotMap.Intake.SystemsPos> getNeighbors() {
        return neighbors;
    }


}
