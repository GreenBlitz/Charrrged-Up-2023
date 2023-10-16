package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw.ClawState;
import java.util.Collections;
import java.util.LinkedList;

public class NodeArm {
    private boolean griperPos; //true - open, close - false
    private ClawState clawPos;
    private boolean isNeighborsSet;
    private final LinkedList<RobotMap.TelescopicArm.PresetPositions> neighbors;
    private final double anglePos;
    private final double extendPos;
    public NodeArm( double extenderPos, double anglePos){
        this.extendPos = extenderPos;
        this.anglePos = anglePos;
        clawPos = ClawState.CONE_MODE;
        neighbors = new LinkedList<RobotMap.TelescopicArm.PresetPositions>();
        isNeighborsSet = false;
    }
    public double getExtendPos() {
        return extendPos;
    }
    public double getAnglePos() {
        return anglePos;
    }
    public void addNeighbors(RobotMap.TelescopicArm.PresetPositions[] neighbors) {
        if(!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }
    public LinkedList<RobotMap.TelescopicArm.PresetPositions> getNeighbors(){
        return neighbors;
    }

    public void setClawPos(ClawState clawPos) {
        this.clawPos = clawPos;
    }

    public ClawState getClawPos() {
        return clawPos;
    }

    public boolean isGriperPos() {
        return griperPos;
    }

    public void setGriperPos(boolean griperPos) {
        this.griperPos = griperPos;
    }
}
