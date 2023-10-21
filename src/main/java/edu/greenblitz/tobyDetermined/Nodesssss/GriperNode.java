package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.greenblitz.utils.GBCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class GriperNode {
    private LinkedList<RobotMap.TelescopicArm.PresetPositions> armMustBe;
    private boolean isNeighborsSet;
    private final LinkedList<RobotMap.Intake.GriperPos> neighbors;
    private GBCommand command;
    public GriperNode(GBCommand command){
        neighbors = new LinkedList<>();
        armMustBe = new LinkedList<>();
        isNeighborsSet = false;
        this.command = command;

    }
    public void addNeighbors(RobotMap.Intake.GriperPos[] neighbors) {
        if(!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }

    public LinkedList<RobotMap.TelescopicArm.PresetPositions> getArmMustBe() {
        return armMustBe;
    }

    public void setArmMustBe(RobotMap.TelescopicArm.PresetPositions[] armMustBe) {
        if(!isNeighborsSet) {
            Collections.addAll(this.armMustBe, armMustBe);
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
