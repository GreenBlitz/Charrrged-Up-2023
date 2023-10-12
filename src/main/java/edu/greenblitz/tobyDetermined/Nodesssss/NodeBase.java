package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.wpi.first.math.util.Units;

import java.util.HashMap;


import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.STARTING_ANGLE_RELATIVE_TO_GROUND;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.PresetPositions.*;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.PresetPositions;

public class NodeBase {
    private RobotMap.TelescopicArm.PresetPositions currentNode;

    protected final HashMap<RobotMap.TelescopicArm.PresetPositions, NodeArm> nodeMap = new HashMap<>();
    private static NodeBase instance;
    private final double TOLERANCE_ANGLE = Units.degreesToRadians(3);

    private final double TOLERANCE_LENGTH = 0.04;//In Meters

    public static NodeBase getInstance() {
        init();
        return instance;
    }

    public static void init() {
        if (instance == null) {
            instance = new NodeBase();
        }
    }

    public NodeBase() {
        /*
        double[][] poses = {{1,5},{2,6}};

      for (int j = 0; j < poses.length; j++) {
            addToList(new NodeArm(j, poses[j][0],poses[j][1]), list);

            list.get(0).setNeighbors();
        }*///todo change list adding to this format
        nodeMap.put(CONE_HIGH, new NodeArm(0.71, Math.toRadians(25.1) - STARTING_ANGLE_RELATIVE_TO_GROUND));
        nodeMap.put(CONE_MID, new NodeArm(0.31, /*1.94*/ Math.toRadians(107)));
        nodeMap.put(CUBE_HIGH, new NodeArm(0.450, Math.toRadians(15.46) - STARTING_ANGLE_RELATIVE_TO_GROUND));
        nodeMap.put(CUBE_MID, new NodeArm(0.29, 1.85));
        nodeMap.put(LOW, new NodeArm(0.35, Math.toRadians(60)));
        nodeMap.put(ZIG_HAIL, new NodeArm(0, Math.toRadians(20.7) - STARTING_ANGLE_RELATIVE_TO_GROUND));
        nodeMap.put(INTAKE_GRAB_CONE_POSITION, new NodeArm(0.34, 0.123));
        nodeMap.put(INTAKE_GRAB_CUBE_POSITION, new NodeArm(0.25, 0.123));
        nodeMap.put(REST_ABOVE_BELLY, new NodeArm(-0.02, 0.196));
        nodeMap.put(PRE_CONE_DROP, new NodeArm(0.089, 0.667));
        nodeMap.put(POST_CONE_DROP, new NodeArm(0.080, 0.1));

        nodeMap.get(CONE_HIGH).addNeighbors(new PresetPositions[]{CONE_MID, CUBE_HIGH, CUBE_MID, ZIG_HAIL, PRE_CONE_DROP});
        nodeMap.get(CONE_MID).addNeighbors(new PresetPositions[]{CONE_HIGH, CUBE_HIGH, CUBE_MID, ZIG_HAIL, PRE_CONE_DROP});
        nodeMap.get(CUBE_HIGH).addNeighbors(new PresetPositions[]{CONE_MID, CONE_HIGH, CUBE_MID, ZIG_HAIL, PRE_CONE_DROP});
        nodeMap.get(CUBE_MID).addNeighbors(new PresetPositions[]{CONE_MID, CUBE_HIGH, CONE_HIGH, ZIG_HAIL, PRE_CONE_DROP});
        nodeMap.get(LOW).addNeighbors(new PresetPositions[]{ZIG_HAIL});
        nodeMap.get(ZIG_HAIL).addNeighbors(new PresetPositions[]{CONE_MID, CUBE_HIGH, LOW, CUBE_MID, CONE_HIGH, REST_ABOVE_BELLY, PRE_CONE_DROP});
        nodeMap.get(INTAKE_GRAB_CONE_POSITION).addNeighbors(new PresetPositions[]{REST_ABOVE_BELLY, INTAKE_GRAB_CUBE_POSITION});
        nodeMap.get(INTAKE_GRAB_CUBE_POSITION).addNeighbors(new PresetPositions[]{REST_ABOVE_BELLY, INTAKE_GRAB_CONE_POSITION});
        nodeMap.get(REST_ABOVE_BELLY).addNeighbors(new PresetPositions[]{ZIG_HAIL, INTAKE_GRAB_CONE_POSITION, INTAKE_GRAB_CUBE_POSITION, PRE_CONE_DROP});
        nodeMap.get(PRE_CONE_DROP).addNeighbors(new PresetPositions[]{POST_CONE_DROP});
        nodeMap.get(POST_CONE_DROP).addNeighbors(new PresetPositions[]{REST_ABOVE_BELLY, INTAKE_GRAB_CONE_POSITION, INTAKE_GRAB_CUBE_POSITION});

        currentNode = INTAKE_GRAB_CONE_POSITION;

        nodeMap.get(CONE_HIGH).setClawPos(Claw.ClawState.RELEASE);
        nodeMap.get(CONE_MID).setClawPos(Claw.ClawState.RELEASE);
        nodeMap.get(CUBE_HIGH).setClawPos(Claw.ClawState.RELEASE);
        nodeMap.get(CUBE_MID).setClawPos(Claw.ClawState.RELEASE);
        nodeMap.get(LOW).setClawPos(Claw.ClawState.RELEASE);
        nodeMap.get(INTAKE_GRAB_CONE_POSITION).setClawPos(Claw.ClawState.CONE_MODE);
        nodeMap.get(PRE_CONE_DROP).setClawPos(Claw.ClawState.CONE_MODE);
        nodeMap.get(POST_CONE_DROP).setClawPos(Claw.ClawState.CONE_MODE);
        nodeMap.get(INTAKE_GRAB_CUBE_POSITION).setClawPos(Claw.ClawState.CUBE_MODE);

    }

    public RobotMap.TelescopicArm.PresetPositions getCurrentNode() {
        if (currentNode == null)
            currentNode = INTAKE_GRAB_CONE_POSITION;
        return currentNode;
    }

    public void setCurrentNode(RobotMap.TelescopicArm.PresetPositions pos) {
        currentNode = pos;
    }

    public NodeArm getNode(RobotMap.TelescopicArm.PresetPositions specNode) {
        return nodeMap.get(specNode);
    }

    public boolean getIfInLength(double length, NodeArm index) {
        return Math.abs(index.getExtendPos() - length) <= TOLERANCE_LENGTH;
    }

    public boolean getIfInAngle(double angle, NodeArm index) {
        return Math.abs(index.getAnglePos() - angle) <= TOLERANCE_ANGLE;

    }

    public boolean getIfInNode(double angle, double length, NodeArm index) {
        return getIfInAngle(angle, index) && getIfInLength(length, index);

    }

    public double getDistanceBetweenTwoPoints(NodeArm a, NodeArm b) {
        return Math.sqrt(
                Math.pow(a.getAnglePos() - b.getAnglePos(), 2)
                        +
                        Math.pow(a.getExtendPos() - b.getExtendPos(), 2));
    }

}
