package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw.ClawState;
import edu.greenblitz.utils.GBCommand;

import java.util.Collections;
import java.util.LinkedList;

public class NodeArm extends GBNode {
    public enum ArmPointer{
        ARM_POINTER;
    }
    private final LinkedList<RobotMap.Intake.SystemsPos> griperMustBe;
    private ClawState clawPos;
    private boolean isNeighborsSet;
    private final LinkedList<RobotMap.Intake.SystemsPos> neighbors;
    private final double anglePos;
    private final double extendPos;
    private GBCommand command;

    public NodeArm( double extenderPos, double anglePos, GBCommand command){
        super(command);
        this.extendPos = extenderPos;
        this.anglePos = anglePos;
        clawPos = ClawState.CONE_MODE;
        neighbors = new LinkedList<RobotMap.Intake.SystemsPos>();
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
    public void setOtherSystemMustBe(RobotMap.Intake.SystemsPos[] griperMustBe) {
        Collections.addAll(this.griperMustBe, griperMustBe);
    }

    @Override
    public LinkedList<RobotMap.Intake.SystemsPos> getOtherSystemMustBe() {
        return griperMustBe;
    }

    public void addNeighbors(RobotMap.Intake.SystemsPos[] neighbors) {
        if(!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }
    public LinkedList<RobotMap.Intake.SystemsPos> getNeighbors(){
        return neighbors;
    }

    public void setClawPos(ClawState clawPos) {
        this.clawPos = clawPos;
    }

    public ClawState getClawPos() {
        return clawPos;
    }

    @Override
    public double getCost(RobotMap.Intake.SystemsPos nodeArm){
        return Math.sqrt(
                Math.pow(getAnglePos() - NodeBase.getNode(nodeArm, ArmPointer.ARM_POINTER).getAnglePos(), 2)
                        +
                        Math.pow(getExtendPos() - NodeBase.getNode(nodeArm, ArmPointer.ARM_POINTER).getExtendPos(), 2));
    }
}
