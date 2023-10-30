package edu.greenblitz.tobyDetermined.Nodesssss;

import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos;
import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw.ClawState;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.math.util.Units;

import java.util.Collections;
import java.util.LinkedList;

public class NodeArm extends GBNode {

    private final static double TOLERANCE_ANGLE = Units.degreesToRadians(3);
    private final static double TOLERANCE_LENGTH = 0.04;//In Meters

    private final LinkedList<SystemsPos> griperMustBe;
    private ClawState clawPos;
    private boolean isNeighborsSet;
    private final LinkedList<SystemsPos> neighbors;
    private final double anglePos;
    private final double extendPos;
    private final GBCommand command;

    public NodeArm(double extenderPos, double anglePos, GBCommand command) {
        super(command);
        this.extendPos = extenderPos;
        this.anglePos = anglePos;
        clawPos = ClawState.CONE_MODE;
        neighbors = new LinkedList<>();
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

    @Override
    public void setOtherSystemMustBe(SystemsPos[] griperMustBe) {
        Collections.addAll(this.griperMustBe, griperMustBe);
    }

    @Override
    public LinkedList<SystemsPos> getOtherSystemMustBe() {
        return griperMustBe;
    }

    public void addNeighbors(SystemsPos[] neighbors) {
        if (!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }

    public LinkedList<SystemsPos> getNeighbors() {
        return neighbors;
    }

    public void setClawPos(ClawState clawPos) {
        this.clawPos = clawPos;
    }

    public ClawState getClawPos() {
        return clawPos;
    }

    public boolean getIsAtLength(double length) {
        return Math.abs(extendPos - length) <= TOLERANCE_LENGTH;
    }

    public boolean getIsAtAngle(double angle) {
        return Math.abs(anglePos - angle) <= TOLERANCE_ANGLE;

    }

    @Override
    public boolean getIsAtNode() {
        return getIsAtAngle(anglePos) && getIsAtLength(extendPos);

    }

}
