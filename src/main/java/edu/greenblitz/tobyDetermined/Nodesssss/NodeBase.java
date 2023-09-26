package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.wpi.first.math.util.Units;

import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.STARTING_ANGLE_RELATIVE_TO_GROUND;

public class NodeBase  {
    private NodeArm REST_ABOVE_BELLY;
    private NodeArm INTAKE_GRAB_CUBE_POSITION;
    private NodeArm CONE_HIGH;
    private NodeArm CONE_MID;
    private NodeArm CUBE_HIGH;
    private NodeArm CUBE_MID;
    private NodeArm LOW;
    private NodeArm ZIG_HAIL;
    private NodeArm INTAKE_GRAB_CONE_POSITION;
    private NodeArm PRE_CONE_DROP;
    private NodeArm POST_CONE_DROP;
    private NodeArm currentNode;
    private int i = 1;
    private final LinkedList<NodeArm> list = new LinkedList<>();
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

    public int addToList(NodeArm nodeArm, LinkedList<NodeArm> list){
        list.add(i,nodeArm);
        i=i+1;
        return i;
    }
    public NodeBase(){
        /*
        double[][] poses = {{1,5},{2,6}};

      for (int j = 0; j < poses.length; j++) {
            addToList(new NodeArm(j, poses[j][0],poses[j][1]), list);

            list.get(0).setNeighbors();
        }*///todo change list adding to this format

        list.add(0,null);
        i = addToList(CONE_HIGH = new NodeArm( i, 0.71, Math.toRadians(25.1) - STARTING_ANGLE_RELATIVE_TO_GROUND), list);
        i = addToList(CONE_MID = new NodeArm( i, 0.31, /*1.94*/ Math.toRadians(107)),list);
        i = addToList(CUBE_HIGH = new NodeArm( i, 0.450, Math.toRadians(15.46) - STARTING_ANGLE_RELATIVE_TO_GROUND),list);
        i = addToList(CUBE_MID = new NodeArm( i, 0.29, 1.85),list);
        i = addToList(LOW = new NodeArm( i, 0.35, Math.toRadians(60)),list);
        i = addToList(ZIG_HAIL = new NodeArm( i, 0, Math.toRadians(20.7) - STARTING_ANGLE_RELATIVE_TO_GROUND),list);
        i = addToList(INTAKE_GRAB_CONE_POSITION = new NodeArm( i, 0.34, 0.123),list);
        i = addToList(INTAKE_GRAB_CUBE_POSITION = new NodeArm(i,0.25, 0.123),list);
        i = addToList(REST_ABOVE_BELLY = new NodeArm(i,-0.02,0.196),list);
        i = addToList(PRE_CONE_DROP = new NodeArm(i,0.089,0.667),list);
        i = addToList(POST_CONE_DROP = new NodeArm(i,0.089,0.1),list);

        CONE_HIGH.setNeighbors(new NodeArm[] {CONE_MID, CUBE_HIGH, LOW, CUBE_MID, ZIG_HAIL,PRE_CONE_DROP});
        CONE_MID.setNeighbors(new NodeArm[] {CONE_HIGH, CUBE_HIGH, LOW, CUBE_MID, ZIG_HAIL,PRE_CONE_DROP});
        CUBE_HIGH.setNeighbors(new NodeArm[] {CONE_MID, CONE_HIGH, LOW, CUBE_MID, ZIG_HAIL,PRE_CONE_DROP});
        CUBE_MID.setNeighbors(new NodeArm[] {CONE_MID, CUBE_HIGH, LOW, CONE_HIGH, ZIG_HAIL,PRE_CONE_DROP});
        LOW.setNeighbors(new NodeArm[] {CONE_MID, CUBE_HIGH, CONE_HIGH, CUBE_MID, ZIG_HAIL,PRE_CONE_DROP});
        ZIG_HAIL.setNeighbors(new NodeArm[] {CONE_MID, CUBE_HIGH, LOW, CUBE_MID, CONE_HIGH,REST_ABOVE_BELLY,PRE_CONE_DROP});
        INTAKE_GRAB_CONE_POSITION.setNeighbors(new NodeArm[] {REST_ABOVE_BELLY,INTAKE_GRAB_CUBE_POSITION});
        INTAKE_GRAB_CUBE_POSITION.setNeighbors(new NodeArm[] {REST_ABOVE_BELLY,INTAKE_GRAB_CONE_POSITION});
        REST_ABOVE_BELLY.setNeighbors(new NodeArm[] {ZIG_HAIL,INTAKE_GRAB_CONE_POSITION,INTAKE_GRAB_CUBE_POSITION,PRE_CONE_DROP});
        PRE_CONE_DROP.setNeighbors(new NodeArm[] {POST_CONE_DROP});
        POST_CONE_DROP.setNeighbors(new NodeArm[] {REST_ABOVE_BELLY,INTAKE_GRAB_CONE_POSITION,INTAKE_GRAB_CUBE_POSITION});
        currentNode = INTAKE_GRAB_CONE_POSITION;
    }
    public enum SpecificNode {

        CONE_HIGH,
        CONE_MID,
        CUBE_HIGH,
        CUBE_MID,
        LOW,
        ZIG_HAIL,
        INTAKE_GRAB_CONE_POSITION,
        INTAKE_GRAB_CUBE_POSITION,
        REST_ABOVE_BELLY,
        PRE_CONE_DROP,
        POST_CONE_DROP

    }

    public NodeArm getCurrentNode() {
        if (currentNode == null)
            currentNode = INTAKE_GRAB_CONE_POSITION;
        return currentNode;
    }
    public void setCurrentNode(NodeArm nodeArm){
        currentNode = nodeArm;
    }

    public NodeArm getNode(SpecificNode specNode) {
        switch (specNode) {
            case CONE_HIGH:
                return CONE_HIGH;
            case CONE_MID:
                return CONE_MID;
            case CUBE_HIGH:
                return CUBE_HIGH;
            case CUBE_MID:
                return CUBE_MID;
            case LOW:
                return LOW;
            case ZIG_HAIL:
                return ZIG_HAIL;
            case INTAKE_GRAB_CONE_POSITION:
                return INTAKE_GRAB_CONE_POSITION;
            case INTAKE_GRAB_CUBE_POSITION:
                return INTAKE_GRAB_CUBE_POSITION;
            case REST_ABOVE_BELLY:
                return REST_ABOVE_BELLY;
            case PRE_CONE_DROP:
                return PRE_CONE_DROP;
            case POST_CONE_DROP:
                return POST_CONE_DROP;
        }
        return null;
    }

    public boolean getIfInLength(double length, NodeArm index) {
        return Math.abs(index.getExtendPos() - length) <= TOLERANCE_LENGTH;
    }
    public boolean getIfInAngle(double angle, NodeArm index) {
        return Math.abs(index.getAnglePos() - angle) <= TOLERANCE_ANGLE;

    }
    public boolean getIfInNode(double angle , double length,  NodeArm index){
        return getIfInAngle(angle,index) && getIfInLength(length,index);

    }
    public double getDistanceBetweenToPoints(NodeArm a, NodeArm b ){
        return Math.sqrt(
                Math.pow(a.getAnglePos()-b.getAnglePos(), 2)
                +
                Math.pow(a.getExtendPos()-b.getExtendPos(), 2));
    }

}
