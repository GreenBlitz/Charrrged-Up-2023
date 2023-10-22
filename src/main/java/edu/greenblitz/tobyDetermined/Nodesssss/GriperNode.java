package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.GBCommand;

import java.util.Collections;
import java.util.LinkedList;

public class GriperNode extends GBNode {
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
        isNeighborsSet = false;
        this.command = command;
        x = 90.0;
        armMustBe = new LinkedList<>();
    }
    public void addNeighbors(RobotMap.Intake.GriperPos[] neighbors) {
        if(!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }
    @Override
    public LinkedList<RobotMap.Intake.GriperPos> getOtherSystemMustBe() {
        return armMustBe;
    }

    @Override
    public void setOtherSystemMustBe(RobotMap.Intake.GriperPos[] griperMustBe) {
        Collections.addAll(this.armMustBe, griperMustBe);
    }


    public GBCommand getCommand() {
        return command;
    }

    public LinkedList<RobotMap.Intake.GriperPos> getNeighbors(){
        return neighbors;
    }
    @Override
    public double getCost(RobotMap.Intake.GriperPos griperNode){
       // return this.x + NodeBase.getNode(griperNode, GriperPointer.GRIPER_POINTER).x;
        return 1;
    }


}
