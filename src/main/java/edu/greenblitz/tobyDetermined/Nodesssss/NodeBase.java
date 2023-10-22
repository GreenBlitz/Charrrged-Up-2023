package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.commands.ArmCommand;
import edu.greenblitz.tobyDetermined.commands.GriperCommand;
import edu.greenblitz.tobyDetermined.commands.NodeToNeighbourCommand;
import edu.greenblitz.tobyDetermined.commands.intake.ExtendAndRoll;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.wpi.first.math.util.Units;

import java.util.HashMap;


import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.STARTING_ANGLE_RELATIVE_TO_GROUND;
//import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.PresetPositions;
//import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.PresetPositions.*;


public class NodeBase {

    protected final static HashMap<GriperPos, NodeArm> nodeMapArm = new HashMap<>();
    protected final static HashMap<GriperPos, GriperNode> nodeMapGrip = new HashMap<>();

    private final static double TOLERANCE_ANGLE = Units.degreesToRadians(3);

    private final static double TOLERANCE_LENGTH = 0.04;//In Meters

    static {
        nodeMapGrip.put(GRIPER_ONE, new GriperNode( new GriperCommand()));
        nodeMapGrip.put(GRIPER_TWO, new GriperNode( new GriperCommand()));
        nodeMapGrip.put(GRIPER_THREE, new GriperNode( new GriperCommand()));

        nodeMapGrip.get(GRIPER_ONE).addNeighbors(new GriperPos[]{GRIPER_TWO});
        nodeMapGrip.get(GRIPER_TWO).addNeighbors(new GriperPos[]{GRIPER_ONE,GRIPER_THREE});
        nodeMapGrip.get(GRIPER_THREE).addNeighbors(new GriperPos[]{GRIPER_TWO});

        nodeMapGrip.get(GRIPER_ONE).setOtherSystemMustBe(new GriperPos[]{ZIG_HAIL, CONE_HIGH, CUBE_HIGH});
        nodeMapGrip.get(GRIPER_TWO).setOtherSystemMustBe(new GriperPos[]{ZIG_HAIL, CONE_HIGH, CUBE_HIGH});
        nodeMapGrip.get(GRIPER_THREE).setOtherSystemMustBe(new GriperPos[]{ZIG_HAIL, CONE_HIGH, CUBE_HIGH});


        /*
        double[][] poses = {{1,5},{2,6}};

      for (int j = 0; j < poses.length; j++) {
            addToList(new NodeArm(j, poses[j][0],poses[j][1]), list);

            list.get(0).setNeighbors();
        }*///todo change list adding to this format
        nodeMapArm.put(CONE_HIGH, new NodeArm(0.71, Math.toRadians(25.1) - STARTING_ANGLE_RELATIVE_TO_GROUND, new ArmCommand()));
        nodeMapArm.put(CONE_MID, new NodeArm(0.31, /*1.94*/ Math.toRadians(107), new ArmCommand()));
        nodeMapArm.put(CUBE_HIGH, new NodeArm(0.450, Math.toRadians(15.46) - STARTING_ANGLE_RELATIVE_TO_GROUND,new ArmCommand()));
        nodeMapArm.put(CUBE_MID, new NodeArm(0.29, 1.85, new ArmCommand()));
        nodeMapArm.put(LOW, new NodeArm(0.35, Math.toRadians(60), new ArmCommand()));
        nodeMapArm.put(ZIG_HAIL, new NodeArm(0, Math.toRadians(20.7) - STARTING_ANGLE_RELATIVE_TO_GROUND, new ArmCommand()));
        nodeMapArm.put(INTAKE_GRAB_CONE_POSITION, new NodeArm(0.34, 0.123, new ArmCommand()));
        nodeMapArm.put(INTAKE_GRAB_CUBE_POSITION, new NodeArm(0.25, 0.123, new ArmCommand()));
        nodeMapArm.put(REST_ABOVE_BELLY, new NodeArm(-0.02, 0.196, new ArmCommand()));
        nodeMapArm.put(PRE_CONE_DROP, new NodeArm(0.089, 0.667, new ArmCommand()));
        nodeMapArm.put(POST_CONE_DROP, new NodeArm(0.080, 0.1, new ArmCommand()));

        nodeMapArm.get(CONE_HIGH).addNeighbors(new GriperPos[]{CONE_MID, CUBE_HIGH, CUBE_MID, ZIG_HAIL, PRE_CONE_DROP});
        nodeMapArm.get(CONE_MID).addNeighbors(new GriperPos[]{CONE_HIGH, CUBE_HIGH, CUBE_MID, ZIG_HAIL, PRE_CONE_DROP});
        nodeMapArm.get(CUBE_HIGH).addNeighbors(new GriperPos[]{CONE_MID, CONE_HIGH, CUBE_MID, ZIG_HAIL, PRE_CONE_DROP});
        nodeMapArm.get(CUBE_MID).addNeighbors(new GriperPos[]{CONE_MID, CUBE_HIGH, CONE_HIGH, ZIG_HAIL, PRE_CONE_DROP});
        nodeMapArm.get(LOW).addNeighbors(new GriperPos[]{ZIG_HAIL});
        nodeMapArm.get(ZIG_HAIL).addNeighbors(new GriperPos[]{CONE_MID, CUBE_HIGH, LOW, CUBE_MID, CONE_HIGH, REST_ABOVE_BELLY, PRE_CONE_DROP});
        nodeMapArm.get(INTAKE_GRAB_CONE_POSITION).addNeighbors(new GriperPos[]{REST_ABOVE_BELLY, INTAKE_GRAB_CUBE_POSITION});
        nodeMapArm.get(INTAKE_GRAB_CUBE_POSITION).addNeighbors(new GriperPos[]{REST_ABOVE_BELLY, INTAKE_GRAB_CONE_POSITION});
        nodeMapArm.get(REST_ABOVE_BELLY).addNeighbors(new GriperPos[]{ZIG_HAIL, INTAKE_GRAB_CONE_POSITION, INTAKE_GRAB_CUBE_POSITION, PRE_CONE_DROP});
        nodeMapArm.get(PRE_CONE_DROP).addNeighbors(new GriperPos[]{POST_CONE_DROP});
        nodeMapArm.get(POST_CONE_DROP).addNeighbors(new GriperPos[]{REST_ABOVE_BELLY, INTAKE_GRAB_CONE_POSITION, INTAKE_GRAB_CUBE_POSITION});

        nodeMapArm.get(CONE_HIGH).setClawPos(Claw.ClawState.RELEASE);
        nodeMapArm.get(CONE_MID).setClawPos(Claw.ClawState.RELEASE);
        nodeMapArm.get(CUBE_HIGH).setClawPos(Claw.ClawState.RELEASE);
        nodeMapArm.get(CUBE_MID).setClawPos(Claw.ClawState.RELEASE);
        nodeMapArm.get(LOW).setClawPos(Claw.ClawState.RELEASE);
        nodeMapArm.get(INTAKE_GRAB_CONE_POSITION).setClawPos(Claw.ClawState.CONE_MODE);
        nodeMapArm.get(PRE_CONE_DROP).setClawPos(Claw.ClawState.CONE_MODE);
        nodeMapArm.get(POST_CONE_DROP).setClawPos(Claw.ClawState.CONE_MODE);
        nodeMapArm.get(INTAKE_GRAB_CUBE_POSITION).setClawPos(Claw.ClawState.CUBE_MODE);

        nodeMapArm.get(CONE_HIGH).setOtherSystemMustBe(new GriperPos[]{GRIPER_ONE, GRIPER_THREE});
        nodeMapArm.get(CONE_MID).setOtherSystemMustBe(new GriperPos[]{GRIPER_ONE, GRIPER_THREE});
        nodeMapArm.get(CUBE_HIGH).setOtherSystemMustBe(new GriperPos[]{GRIPER_ONE, GRIPER_THREE});
        nodeMapArm.get(CUBE_MID).setOtherSystemMustBe(new GriperPos[]{GRIPER_ONE, GRIPER_THREE});
        nodeMapArm.get(LOW).setOtherSystemMustBe(new GriperPos[]{GRIPER_ONE, GRIPER_THREE});
        nodeMapArm.get(INTAKE_GRAB_CONE_POSITION).setOtherSystemMustBe(new GriperPos[]{GRIPER_TWO});
        nodeMapArm.get(INTAKE_GRAB_CUBE_POSITION).setOtherSystemMustBe(new GriperPos[]{GRIPER_TWO});

    }

    public static NodeArm getNode(GriperPos specNode, NodeArm.ArmPointer armPointer) {
            return nodeMapArm.get(specNode);
    }
    public static GriperNode getNode(GriperPos specNode, GriperNode.GriperPointer a) {
        return nodeMapGrip.get(specNode);
    }

    public static GBNode getNode(GriperPos specNode){
        if(specNode.toString().contains("GRIPER")){
            return nodeMapGrip.get(specNode);
        }
        return nodeMapArm.get(specNode);
    }
//    public static void getNode(String str) {
//       nodeMapGrip.
//       return nodeMapGrip.get(specNode);
//
//    }
        public static GriperPos getGripPos(GriperPos specPos){
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
                default:
                    return GRIPER_ONE;

            }
        }


    public static boolean getIfInLength(double length, NodeArm index) {
        return Math.abs(index.getExtendPos() - length) <= TOLERANCE_LENGTH;
    }

    public static boolean getIfInAngle(double angle, NodeArm index) {
        return Math.abs(index.getAnglePos() - angle) <= TOLERANCE_ANGLE;

    }

    public static boolean getIfInNode(double angle, double length, NodeArm index) {
        return getIfInAngle(angle, index) && getIfInLength(length, index);

    }
}
