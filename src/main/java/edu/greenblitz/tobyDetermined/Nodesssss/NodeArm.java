package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw.ClawState;
import edu.wpi.first.math.util.Units;

import java.util.Collections;
import java.util.LinkedList;

public class NodeArm {
    private ClawState clawPos;

    private boolean isNeighborsSet;
    private final LinkedList<RobotMap.TelescopicArm.PresetPositions> neighbors;

    private final double anglePos;
    private final double extendPos;

    private final static double TOLERANCE_ANGLE = Units.degreesToRadians(2);
    private final static double TOLERANCE_LENGTH = 0.01;//In Meters

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

    public boolean getIsAtLength(double length) {
        return Math.abs(extendPos - length) <= TOLERANCE_LENGTH || (extendPos < 0.1 && length < 0.1);
    }

    public boolean getIsAtAngle(double angle) {
        return Math.abs(anglePos - angle) <= TOLERANCE_ANGLE ;

    }

    public boolean getIsAtNode(double angle, double length) {
        return getIsAtAngle(angle) && getIsAtLength(length);

    }
    
}
