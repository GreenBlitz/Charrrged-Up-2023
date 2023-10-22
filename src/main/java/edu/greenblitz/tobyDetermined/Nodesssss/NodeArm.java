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

    @Override
    public void setOtherSystemMustBe(RobotMap.Intake.GriperPos[] griperMustBe) {
        Collections.addAll(this.griperMustBe, griperMustBe);
    }

    @Override
    public LinkedList<RobotMap.Intake.GriperPos> getOtherSystemMustBe() {
        return griperMustBe;
    }

    public void addNeighbors(RobotMap.Intake.GriperPos[] neighbors) {
        if(!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }
    public LinkedList<RobotMap.Intake.GriperPos> getNeighbors(){
        return neighbors;
    }

    public void setClawPos(ClawState clawPos) {
        this.clawPos = clawPos;
    }

    public ClawState getClawPos() {
        return clawPos;
    }

    @Override
    public double getCost(RobotMap.Intake.GriperPos nodeArm){
        return Math.sqrt(
                Math.pow(getAnglePos() - NodeBase.getNode(nodeArm, ArmPointer.ARM_POINTER).getAnglePos(), 2)
                        +
                        Math.pow(getExtendPos() - NodeBase.getNode(nodeArm, ArmPointer.ARM_POINTER).getExtendPos(), 2));
    }
    public static void main(String[] args){

        System.out.println(NodeBase.getNode(RobotMap.Intake.GriperPos.LOW).getCost(RobotMap.Intake.GriperPos.CONE_HIGH));
    }

}
