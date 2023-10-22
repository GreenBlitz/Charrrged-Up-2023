package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.GBCommand;

import java.util.Collections;
import java.util.LinkedList;

public class GriperNode extends GBNode<RobotMap.Intake.GriperPos > {
    public enum GriperPointer{
        GRIPER_POINTER;
    }
    private LinkedList<RobotMap.Intake.GriperPos> armMustBe;
    private boolean isNeighborsSet;
    private final LinkedList<RobotMap.Intake.GriperPos> neighbors;
    private GBCommand command;
    private double x;

    public GriperNode(GBCommand command){
        super(command);
        neighbors = new LinkedList<>();
        armMustBe = new LinkedList<>();
        isNeighborsSet = false;
        this.command = command;
        x = 90.0;
    }
    public void addNeighbors(RobotMap.Intake.GriperPos[] neighbors) {
        if(!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }

    public LinkedList<RobotMap.Intake.GriperPos> getArmMustBe() {
        return armMustBe;
    }

    public void setArmMustBe(RobotMap.Intake.GriperPos[] armMustBe) {
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


    public double getCost(RobotMap.Intake.GriperPos griperNode){
        return this.x;
    }


}
