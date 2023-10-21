package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw.ClawState;
import edu.greenblitz.utils.GBCommand;

import java.util.Collections;
import java.util.LinkedList;

public class NodeArm extends GBNode{
    private final LinkedList<RobotMap.Intake.GriperPos> griperMustBe;
    private ClawState clawPos;
    private boolean isNeighborsSet;
    private final LinkedList<RobotMap.Intake.GriperPos> neighbors;
    private final double anglePos;
    private final double extendPos;
    private GBCommand command;

    public NodeArm( double extenderPos, double anglePos, GBCommand command){
        super(command);
        this.extendPos = extenderPos;
        this.anglePos = anglePos;
        clawPos = ClawState.CONE_MODE;
        neighbors = new LinkedList<RobotMap.Intake.GriperPos>();
        isNeighborsSet = false;
        griperMustBe = new LinkedList<>();
        this.command = command;
    }
    public double getExtendPos() {
        return extendPos;
    }
    public double getAnglePos() {
        return anglePos;
    }

    public void setGriperMustBe(RobotMap.Intake.GriperPos[] griperMustBe) {
        Collections.addAll(this.griperMustBe, griperMustBe);
    }

    public void getGriperMustBe() {
        return griperMustBe;
    }

    public void addNeighbors(RobotMap.Intake.GriperPos[] neighbors) {
        if(!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }
    @Override
    public LinkedList<RobotMap.Intake.GriperPos> getNeighbors(){
        return neighbors;
    }

    public void setClawPos(ClawState clawPos) {
        this.clawPos = clawPos;
    }

    public ClawState getClawPos() {
        return clawPos;
    }
}
