package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.wpi.first.math.util.Units;

import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Elbow.STARTING_ANGLE_RELATIVE_TO_GROUND;

public class NodeBase  {
    private  NodeArm REST_ABOVE_BELLY;
    private  NodeArm INTAKE_GRAB_CUBE_POSITION;
    private  NodeArm CONE_HIGH;
    private  NodeArm CONE_MID;
    private  NodeArm CUBE_HIGH;
    private  NodeArm CUBE_MID;
    private  NodeArm LOW;
    private  NodeArm ZIG_HAIL;
    private  NodeArm INTAKE_GRAB_CONE_POSITION;
    private  NodeArm PRE_CONE_DROP;
    private  NodeArm POST_CONE_DROP;
    private  NodeArm currentNode;
    private  int indexOfNodes = 1;
    private  final LinkedList<NodeArm> listOfNodes = new LinkedList<>();
    private static NodeBase instance;
    private  final double TOLERANCE_ANGLE = Units.degreesToRadians(3);

    private  final double TOLERANCE_LENGTH = 0.04;//In Meters

    public static NodeBase getInstance() {
        init();
        return instance;
    }

    public static void init() {
        if (instance == null) {
            instance = new NodeBase();
        }
    }
    public  int addToList(NodeArm nodeArm, LinkedList<NodeArm> list){
        list.add(indexOfNodes,nodeArm);
        indexOfNodes++;
        return indexOfNodes;
    }
    public NodeBase(){
        /*
        double[][] poses = {{1,5},{2,6}};

      for (int j = 0; j < poses.length; j++) {
            addToList(new NodeArm(j, poses[j][0],poses[j][1]), list);

            list.get(0).setNeighbors();
        }*///todo change list adding to this format

        listOfNodes.add(0,null);
        indexOfNodes = addToList(CONE_HIGH = new NodeArm(indexOfNodes, 0.71, Math.toRadians(25.1) - STARTING_ANGLE_RELATIVE_TO_GROUND), listOfNodes);
        indexOfNodes = addToList(CONE_MID = new NodeArm(indexOfNodes, 0.31, /*1.94*/ Math.toRadians(107)), listOfNodes);
        indexOfNodes = addToList(CUBE_HIGH = new NodeArm(indexOfNodes, 0.450, Math.toRadians(15.46) - STARTING_ANGLE_RELATIVE_TO_GROUND), listOfNodes);
        indexOfNodes = addToList(CUBE_MID = new NodeArm(indexOfNodes, 0.29, 1.85), listOfNodes);
        indexOfNodes = addToList(LOW = new NodeArm(indexOfNodes, 0.35, Math.toRadians(60)), listOfNodes);
        indexOfNodes = addToList(ZIG_HAIL = new NodeArm(indexOfNodes, 0, Math.toRadians(20.7) - STARTING_ANGLE_RELATIVE_TO_GROUND), listOfNodes);
        indexOfNodes = addToList(INTAKE_GRAB_CONE_POSITION = new NodeArm(indexOfNodes, 0.34, 0.123), listOfNodes);
        indexOfNodes = addToList(INTAKE_GRAB_CUBE_POSITION = new NodeArm(indexOfNodes,0.25, 0.123), listOfNodes);
        indexOfNodes = addToList(REST_ABOVE_BELLY = new NodeArm(indexOfNodes,-0.02,0.196), listOfNodes);
        indexOfNodes = addToList(PRE_CONE_DROP = new NodeArm(indexOfNodes,0.089,0.667), listOfNodes);
        indexOfNodes = addToList(POST_CONE_DROP = new NodeArm(indexOfNodes,0.080,0.1), listOfNodes);

        CONE_HIGH.addNeighbors(new NodeArm[] {CONE_MID, CUBE_HIGH, CUBE_MID, ZIG_HAIL,PRE_CONE_DROP});
        CONE_MID.addNeighbors(new NodeArm[] {CONE_HIGH, CUBE_HIGH,  CUBE_MID, ZIG_HAIL,PRE_CONE_DROP});
        CUBE_HIGH.addNeighbors(new NodeArm[] {CONE_MID, CONE_HIGH,  CUBE_MID, ZIG_HAIL,PRE_CONE_DROP});
        CUBE_MID.addNeighbors(new NodeArm[] {CONE_MID, CUBE_HIGH,  CONE_HIGH, ZIG_HAIL,PRE_CONE_DROP});
        LOW.addNeighbors(new NodeArm[] {ZIG_HAIL});
        ZIG_HAIL.addNeighbors(new NodeArm[] {CONE_MID, CUBE_HIGH, LOW, CUBE_MID, CONE_HIGH,REST_ABOVE_BELLY,PRE_CONE_DROP});
        INTAKE_GRAB_CONE_POSITION.addNeighbors(new NodeArm[] {REST_ABOVE_BELLY,INTAKE_GRAB_CUBE_POSITION});
        INTAKE_GRAB_CUBE_POSITION.addNeighbors(new NodeArm[] {REST_ABOVE_BELLY,INTAKE_GRAB_CONE_POSITION});
        REST_ABOVE_BELLY.addNeighbors(new NodeArm[] {ZIG_HAIL,INTAKE_GRAB_CONE_POSITION,INTAKE_GRAB_CUBE_POSITION,PRE_CONE_DROP});
        PRE_CONE_DROP.addNeighbors(new NodeArm[] {POST_CONE_DROP});
        POST_CONE_DROP.addNeighbors(new NodeArm[] {REST_ABOVE_BELLY,INTAKE_GRAB_CONE_POSITION,INTAKE_GRAB_CUBE_POSITION});

        currentNode = INTAKE_GRAB_CONE_POSITION;

        CONE_HIGH.setClawPos(3);
        CONE_MID.setClawPos(3);
        CUBE_HIGH.setClawPos(3);
        CUBE_MID.setClawPos(3);
        INTAKE_GRAB_CONE_POSITION.setClawPos(1);
        INTAKE_GRAB_CUBE_POSITION.setClawPos(2);
        PRE_CONE_DROP.setClawPos(1);
        POST_CONE_DROP.setClawPos(1);
        LOW.setClawPos(3);

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

    public  NodeArm getCurrentNode() {
        if (currentNode == null)
            currentNode = INTAKE_GRAB_CONE_POSITION;
        return currentNode;
    }
    public  void setCurrentNode(NodeArm nodeArm){
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

    public  boolean getIfInLength(double length, NodeArm index) {
        return Math.abs(index.getExtendPos() - length) <= TOLERANCE_LENGTH;
    }
    public  boolean getIfInAngle(double angle, NodeArm index) {
        return Math.abs(index.getAnglePos() - angle) <= TOLERANCE_ANGLE;

    }
    public  boolean getIfInNode(double angle , double length,  NodeArm index){
        return getIfInAngle(angle,index) && getIfInLength(length,index);

    }
    public  double getDistanceBetweenTwoPoints(NodeArm a, NodeArm b ){
        return Math.sqrt(
                Math.pow(a.getAnglePos()-b.getAnglePos(), 2)
                +
                Math.pow(a.getExtendPos()-b.getExtendPos(), 2));
    }

}
