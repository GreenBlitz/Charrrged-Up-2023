package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;

import java.util.Collections;
import java.util.LinkedList;

public class NodeArm {
    private int clawPos;//1 = cone catch . 2 = cube catch . 3 = release . 4590 = null
    private boolean isNeighborsSet;
    private final LinkedList<RobotMap.TelescopicArm.PresetPositions> neighbors;
    private final double anglePos;
    private final double extendPos;
    public NodeArm( double extenderPos, double anglePos){
        this.extendPos = extenderPos;
        this.anglePos = anglePos;
        clawPos = 4590;
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

    public void setClawPos(int clawPos) {
        this.clawPos = clawPos;
    }

    public int getClawPos() {
        return clawPos;
    }
}
