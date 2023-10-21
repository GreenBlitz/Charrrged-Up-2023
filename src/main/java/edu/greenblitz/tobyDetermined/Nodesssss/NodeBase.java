package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.commands.ArmCommand;
import edu.greenblitz.tobyDetermined.commands.GriperCommand;
import edu.greenblitz.tobyDetermined.commands.NodeToNeighbourCommand;
import edu.greenblitz.tobyDetermined.commands.intake.ExtendAndRoll;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.wpi.first.math.util.Units;

import java.util.HashMap;


import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.STARTING_ANGLE_RELATIVE_TO_GROUND;
import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos;


public class NodeBase {

    protected final static HashMap<GriperPos, NodeArm> nodeMapArm = new HashMap<>();
    protected final static HashMap<GriperPos, GriperNode> nodeMapGrip = new HashMap<>();

    private final static double TOLERANCE_ANGLE = Units.degreesToRadians(3);

    private final static double TOLERANCE_LENGTH = 0.04;//In Meters

    static {
        nodeMapGrip.put(ONE, new GriperNode( new GriperCommand()));
        nodeMapGrip.put(TWO, new GriperNode( new GriperCommand()));
        nodeMapGrip.put(THREE, new GriperNode( new GriperCommand()));

        nodeMapGrip.get(ONE).addNeighbors(new GriperPos[]{TWO});
        nodeMapGrip.get(ONE).addNeighbors(new GriperPos[]{ONE,THREE});
        nodeMapGrip.get(ONE).addNeighbors(new GriperPos[]{TWO});

        nodeMapGrip.get(ONE).setArmMustBe(new GriperPos[]{ZIG_HAIL, CONE_HIGH, CUBE_HIGH});
        nodeMapGrip.get(TWO).setArmMustBe(new GriperPos[]{ZIG_HAIL, CONE_HIGH, CUBE_HIGH});
        nodeMapGrip.get(THREE).setArmMustBe(new GriperPos[]{ZIG_HAIL, CONE_HIGH, CUBE_HIGH});


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

        nodeMapArm.get(CONE_HIGH).setGriperMustBe(new GriperPos[]{ONE, THREE});
        nodeMapArm.get(CONE_MID).setGriperMustBe(new GriperPos[]{ONE, THREE});
        nodeMapArm.get(CUBE_HIGH).setGriperMustBe(new GriperPos[]{ONE, THREE});
        nodeMapArm.get(CUBE_MID).setGriperMustBe(new GriperPos[]{ONE, THREE});
        nodeMapArm.get(LOW).setGriperMustBe(new GriperPos[]{ONE, THREE});
        nodeMapArm.get(INTAKE_GRAB_CONE_POSITION).setGriperMustBe(new GriperPos[]{TWO});
        nodeMapArm.get(INTAKE_GRAB_CUBE_POSITION).setGriperMustBe(new GriperPos[]{TWO});

    }

    public static GBNode getNode(GriperPos specNode) {
        if(specNode.toString().contains("GRIPER")){
            return nodeMapGrip.get(specNode);
        }
        else
            return nodeMapArm.get(specNode);
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

    public static double getDistanceBetweenTwoPoints(NodeArm a, NodeArm b) {
        return Math.sqrt(
                Math.pow(a.getAnglePos() - b.getAnglePos(), 2)
                        +
                Math.pow(a.getExtendPos() - b.getExtendPos(), 2));
    }
    public static double getCost(GriperNode a, GriperNode b){
        return 1.0;
    }

    public static double getCost() {
        return 1.0;
    }
}
