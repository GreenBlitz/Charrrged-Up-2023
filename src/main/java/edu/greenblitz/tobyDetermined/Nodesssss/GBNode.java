package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.utils.GBCommand;
import edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos;
import edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos.*;
import java.util.Collections;
import java.util.LinkedList;

public abstract class GBNode {
    private boolean isNeighborsSet;
    private final LinkedList<GriperPos> neighbors;
    private GBCommand command;

    private LinkedList<GriperPos> OtherSystemMustBe;
    public GBNode(GBCommand command ){
        neighbors = new LinkedList<GriperPos>();
        isNeighborsSet = false;
        this.command = command;
        OtherSystemMustBe = new LinkedList<>();
    }
    public void addNeighbors(GriperPos[] neighbors) {
        if(!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }
    public void setOtherSystemMustBe(GriperPos[] griperMustBe) {
        Collections.addAll(this.OtherSystemMustBe, griperMustBe);
    }

    public LinkedList<GriperPos> getOtherSystemMustBe() {
        return OtherSystemMustBe;
    }

    public GBCommand getCommand() {
        return command;
    }

    public LinkedList<GriperPos> getNeighbors(){
        return neighbors;
    }
    public double getCost(){return 1.0;}

    public double getCost(GriperPos nodeArm){
        return 0;
    }

}
