package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.math.util.Units;
import org.w3c.dom.Node;

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

        CONE_HIGH.setNeighbors(new NodeArm[] {CONE_MID, CUBE_HIGH, LOW, CUBE_MID, ZIG_HAIL});
        CONE_MID.setNeighbors(new NodeArm[] {CONE_HIGH, CUBE_HIGH, LOW, CUBE_MID, ZIG_HAIL});
        CUBE_HIGH.setNeighbors(new NodeArm[] {CONE_MID, CONE_HIGH, LOW, CUBE_MID, ZIG_HAIL});
        CUBE_MID.setNeighbors(new NodeArm[] {CONE_MID, CUBE_HIGH, LOW, CONE_HIGH, ZIG_HAIL});
        LOW.setNeighbors(new NodeArm[] {CONE_MID, CUBE_HIGH, CONE_HIGH, CUBE_MID, ZIG_HAIL});
        ZIG_HAIL.setNeighbors(new NodeArm[] {CONE_MID, CUBE_HIGH, LOW, CUBE_MID, CONE_HIGH,REST_ABOVE_BELLY});
        INTAKE_GRAB_CONE_POSITION.setNeighbors(new NodeArm[] {REST_ABOVE_BELLY,INTAKE_GRAB_CUBE_POSITION});
        INTAKE_GRAB_CUBE_POSITION.setNeighbors(new NodeArm[] {REST_ABOVE_BELLY,INTAKE_GRAB_CONE_POSITION});
        REST_ABOVE_BELLY.setNeighbors(new NodeArm[] {ZIG_HAIL,INTAKE_GRAB_CONE_POSITION,INTAKE_GRAB_CUBE_POSITION});
        currentNode = INTAKE_GRAB_CONE_POSITION;
    }

    public NodeArm getCurrentNode() {
        return currentNode;
    }
    public void setCurrentNode(NodeArm nodeArm){
        currentNode = nodeArm;
    }

    public NodeArm getNode(int id) {
        for (int j = 1; j < list.size(); j++) {
            NodeArm node = list.get(j);
            if (id == node.getId()) {
                return node;
            }
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
