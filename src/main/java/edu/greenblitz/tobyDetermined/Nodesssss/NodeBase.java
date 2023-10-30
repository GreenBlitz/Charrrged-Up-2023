package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.commands.ArmCommand;
import edu.greenblitz.tobyDetermined.commands.GriperCommand;

import java.util.HashMap;
import java.util.LinkedList;


import static edu.greenblitz.tobyDetermined.RobotMap.Intake.SystemsPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.Intake.SystemsPos;

public class NodeBase {

    protected final static HashMap<SystemsPos, NodeArm> nodeMapArm = new HashMap<>();
    protected final static HashMap<SystemsPos, GriperNode> nodeMapGrip = new HashMap<>();
    protected final static HashMap<SystemsPos, GBNode> nodeMap = new HashMap<>();

    private static final LinkedList<SystemsPos> listGriper = new LinkedList<>();
    private static final LinkedList<SystemsPos> listArm = new LinkedList<>();
    private static String secondSystemName = "GRIPER";

    static {
//        nodeMapGrip.put(GRIPER_ONE, new GriperNode( new GriperCommand()));
//        listGriper.add(GRIPER_ONE);
//        nodeMapGrip.put(GRIPER_TWO, new GriperNode( new GriperCommand()));
//        listGriper.add(GRIPER_TWO);
//        nodeMapGrip.put(GRIPER_THREE, new GriperNode( new GriperCommand()));
//        listGriper.add(GRIPER_THREE);
//        nodeMapGrip.get(GRIPER_ONE).setOtherSystemMustBe(new GriperPos[]{ZIG_HAIL, CONE_HIGH, CUBE_HIGH});
//        nodeMapGrip.get(GRIPER_TWO).setOtherSystemMustBe(new GriperPos[]{ZIG_HAIL, CONE_HIGH, CUBE_HIGH});
//        nodeMapGrip.get(GRIPER_THREE).setOtherSystemMustBe(new GriperPos[]{ZIG_HAIL, CONE_HIGH, CUBE_HIGH});
//
//        nodeMapGrip.get(GRIPER_ONE).addNeighbors(new GriperPos[]{GRIPER_TWO});
//        nodeMapGrip.get(GRIPER_TWO).addNeighbors(new GriperPos[]{GRIPER_ONE, GRIPER_THREE});
//        nodeMapGrip.get(GRIPER_THREE).addNeighbors(new GriperPos[]{GRIPER_TWO});
//
//        /*
//        double[][] poses = {{1,5},{2,6}};
//
//      for (int j = 0; j < poses.length; j++) {
//            addToList(new NodeArm(j, poses[j][0],poses[j][1]), list);
//
//            list.get(0).setNeighbors();
//        }*///todo change list adding to this format
//        nodeMapArm.put(CONE_HIGH, new NodeArm(0.71, Math.toRadians(25.1) - STARTING_ANGLE_RELATIVE_TO_GROUND, new ArmCommand()));
//        nodeMapArm.put(CONE_MID, new NodeArm(0.31, /*1.94*/ Math.toRadians(107), new ArmCommand()));
//        nodeMapArm.put(CUBE_HIGH, new NodeArm(0.450, Math.toRadians(15.46) - STARTING_ANGLE_RELATIVE_TO_GROUND,new ArmCommand()));
//        nodeMapArm.put(CUBE_MID, new NodeArm(0.29, 1.85, new ArmCommand()));
//        nodeMapArm.put(CONE_HIGH, new NodeArm(3, 0, new ArmCommand()));
//        nodeMapArm.put(LOW, new NodeArm(0.35, Math.toRadians(60), new ArmCommand()));
//        nodeMapArm.put(ZIG_HAIL, new NodeArm(0, Math.toRadians(20.7) - STARTING_ANGLE_RELATIVE_TO_GROUND, new ArmCommand()));
//        nodeMapArm.put(INTAKE_GRAB_CONE_POSITION, new NodeArm(0.34, 0.123, new ArmCommand()));
//        nodeMapArm.put(INTAKE_GRAB_CUBE_POSITION, new NodeArm(0.25, 0.123, new ArmCommand()));
//        nodeMapArm.put(REST_ABOVE_BELLY, new NodeArm(-0.02, 0.196, new ArmCommand()));
//        nodeMapArm.put(PRE_CONE_DROP, new NodeArm(0.089, 0.667, new ArmCommand()));
//        nodeMapArm.put(POST_CONE_DROP, new NodeArm(0.080, 0.1, new ArmCommand()));
//        listArm.add(CONE_HIGH);
//        listArm.add(CONE_MID);
//        listArm.add(CUBE_HIGH);
//        listArm.add(CUBE_MID);
//        listArm.add(LOW);
//        listArm.add(ZIG_HAIL);
//        listArm.add(INTAKE_GRAB_CONE_POSITION);
//        listArm.add(INTAKE_GRAB_CUBE_POSITION);
//        listArm.add(REST_ABOVE_BELLY);
//        listArm.add(PRE_CONE_DROP);
//        listArm.add(POST_CONE_DROP);
//
//        nodeMapArm.get(CONE_HIGH).addNeighbors(new GriperPos[]{CONE_MID, CUBE_HIGH, CUBE_MID, ZIG_HAIL, PRE_CONE_DROP});
//        nodeMapArm.get(CONE_MID).addNeighbors(new GriperPos[]{CONE_HIGH, CUBE_HIGH, CUBE_MID, ZIG_HAIL, PRE_CONE_DROP});
//        nodeMapArm.get(CUBE_HIGH).addNeighbors(new GriperPos[]{CONE_MID, CONE_HIGH, CUBE_MID, ZIG_HAIL, PRE_CONE_DROP});
//        nodeMapArm.get(CUBE_MID).addNeighbors(new GriperPos[]{CONE_MID, CUBE_HIGH, CONE_HIGH, ZIG_HAIL, PRE_CONE_DROP});
//        nodeMapArm.get(LOW).addNeighbors(new GriperPos[]{ZIG_HAIL});
//        nodeMapArm.get(ZIG_HAIL).addNeighbors(new GriperPos[]{CONE_MID, CUBE_HIGH, LOW, CUBE_MID, CONE_HIGH, REST_ABOVE_BELLY, PRE_CONE_DROP});
//        nodeMapArm.get(INTAKE_GRAB_CONE_POSITION).addNeighbors(new GriperPos[]{REST_ABOVE_BELLY, INTAKE_GRAB_CUBE_POSITION});
//        nodeMapArm.get(INTAKE_GRAB_CUBE_POSITION).addNeighbors(new GriperPos[]{REST_ABOVE_BELLY, INTAKE_GRAB_CONE_POSITION});
//        nodeMapArm.get(REST_ABOVE_BELLY).addNeighbors(new GriperPos[]{ZIG_HAIL, INTAKE_GRAB_CONE_POSITION, INTAKE_GRAB_CUBE_POSITION, PRE_CONE_DROP});
//        nodeMapArm.get(PRE_CONE_DROP).addNeighbors(new GriperPos[]{POST_CONE_DROP});
//        nodeMapArm.get(POST_CONE_DROP).addNeighbors(new GriperPos[]{REST_ABOVE_BELLY, INTAKE_GRAB_CONE_POSITION, INTAKE_GRAB_CUBE_POSITION});
//
//        nodeMapArm.get(CONE_HIGH).setClawPos(Claw.ClawState.RELEASE);
//        nodeMapArm.get(CONE_MID).setClawPos(Claw.ClawState.RELEASE);
//        nodeMapArm.get(CUBE_HIGH).setClawPos(Claw.ClawState.RELEASE);
//        nodeMapArm.get(CUBE_MID).setClawPos(Claw.ClawState.RELEASE);
//        nodeMapArm.get(LOW).setClawPos(Claw.ClawState.RELEASE);
//        nodeMapArm.get(INTAKE_GRAB_CONE_POSITION).setClawPos(Claw.ClawState.CONE_MODE);
//        nodeMapArm.get(PRE_CONE_DROP).setClawPos(Claw.ClawState.CONE_MODE);
//        nodeMapArm.get(POST_CONE_DROP).setClawPos(Claw.ClawState.CONE_MODE);
//        nodeMapArm.get(INTAKE_GRAB_CUBE_POSITION).setClawPos(Claw.ClawState.CUBE_MODE);
//
//        nodeMapArm.get(CONE_HIGH).setOtherSystemMustBe(new GriperPos[]{GRIPER_ONE, GRIPER_THREE});
//        nodeMapArm.get(CONE_MID).setOtherSystemMustBe(new GriperPos[]{GRIPER_ONE, GRIPER_THREE});
//        nodeMapArm.get(CUBE_HIGH).setOtherSystemMustBe(new GriperPos[]{GRIPER_ONE, GRIPER_THREE});
//        nodeMapArm.get(CUBE_MID).setOtherSystemMustBe(new GriperPos[]{GRIPER_ONE, GRIPER_THREE});
//        nodeMapArm.get(LOW).setOtherSystemMustBe(new GriperPos[]{GRIPER_ONE, GRIPER_THREE});
//        nodeMapArm.get(INTAKE_GRAB_CONE_POSITION).setOtherSystemMustBe(new GriperPos[]{GRIPER_TWO});
//        nodeMapArm.get(INTAKE_GRAB_CUBE_POSITION).setOtherSystemMustBe(new GriperPos[]{GRIPER_TWO});
        nodeMap.put(LOWWW, new NodeArm(0, 0, new ArmCommand()));
        nodeMap.put(GROUND, new NodeArm(1, 0, new ArmCommand()));
        nodeMap.put(MID, new NodeArm(2, 0, new ArmCommand()));
        nodeMap.put(HIGH, new NodeArm(3, 0, new ArmCommand()));
        listArm.add(LOWWW);
        listArm.add(GROUND);
        listArm.add(MID);
        listArm.add(HIGH);

        nodeMap.get(LOWWW).addNeighbors(new SystemsPos[]{GROUND, MID, HIGH});
        nodeMap.get(GROUND).addNeighbors(new SystemsPos[]{LOWWW, MID, HIGH});
        nodeMap.get(MID).addNeighbors(new SystemsPos[]{GROUND, LOWWW, HIGH});
        nodeMap.get(HIGH).addNeighbors(new SystemsPos[]{GROUND, MID, LOWWW});

        nodeMap.put(GRIPER_OPEN, new GriperNode(new GriperCommand()));
        nodeMap.put(GRIPER_CLOSE, new GriperNode(new GriperCommand()));
        listGriper.add(GRIPER_OPEN);
        listGriper.add(GRIPER_CLOSE);

        nodeMap.get(GRIPER_OPEN).addNeighbors(new SystemsPos[]{GRIPER_CLOSE});
        nodeMap.get(GRIPER_CLOSE).addNeighbors(new SystemsPos[]{GRIPER_OPEN});

        nodeMap.get(LOWWW).setOtherSystemMustBe(new SystemsPos[]{GRIPER_OPEN});


    }

    public static GBNode getNode(SystemsPos specNode) {
        return nodeMap.get(specNode);
    }

    public static LinkedList<SystemsPos> getOtherSystemPositions(SystemsPos specNode) {
        LinkedList<SystemsPos> list;
        if (specNode.toString().contains(secondSystemName)) {
            list = new LinkedList<>(listGriper);
        } else {
            list = new LinkedList<>(listArm);
        }
        return list;
    }

    public static SystemsPos getSystemsPos(SystemsPos specPos) {
        switch (specPos) {
            case LOW:
                return LOW;
            case CONE_MID:
                return CONE_MID;
            case CUBE_MID:
                return CUBE_MID;
            case ZIG_HAIL:
                return ZIG_HAIL;
            case INTAKE_GRAB_CUBE_POSITION:
                return INTAKE_GRAB_CUBE_POSITION;
            case INTAKE_GRAB_CONE_POSITION:
                return INTAKE_GRAB_CONE_POSITION;
            case REST_ABOVE_BELLY:
                return REST_ABOVE_BELLY;
            case POST_CONE_DROP:
                return POST_CONE_DROP;
            case PRE_CONE_DROP:
                return PRE_CONE_DROP;
            case CUBE_HIGH:
                return CUBE_HIGH;
            case CONE_HIGH:
                return CONE_HIGH;
            case GRIPER_THREE:
                return GRIPER_THREE;
            case GRIPER_TWO:
                return GRIPER_TWO;
            case GRIPER_ONE:
                return GRIPER_ONE;
            case MID:
                return MID;
            case HIGH:
                return HIGH;
            case LOWWW:
                return LOWWW;
            case GROUND:
                return GROUND;
            case GRIPER_OPEN:
                return GRIPER_OPEN;
            case GRIPER_CLOSE:
                return GRIPER_CLOSE;
            default:
                return null;
        }
    }

}
