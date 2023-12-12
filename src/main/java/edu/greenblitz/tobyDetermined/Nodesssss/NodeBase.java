package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.GriperNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.NodeArm;
import edu.wpi.first.math.Pair;
import java.util.HashMap;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos.*;

public class NodeBase {
    public enum SystemsPos {
        CONE_HIGH,
        CONE_MID,
        CUBE_HIGH,
        CUBE_MID,
        LOW,

        POST_CONE_DROP,
        PRE_CONE_DROP,

        INTAKE_GRAB_CONE_POSITION,
        INTAKE_GRAB_CUBE_POSITION,
        PRE_INTAKE_GRAB_POSITION,

        REST_ABOVE_BELLY,

        ZIG_HAIL,

        GRIPER_ONE,
        GRIPER_TWO,
        GRIPER_THREE,

        ARM_GROUND,
        ARM_LOWWW,
        ARM_MID,
        ARM_HIGH,

        GRIPER_OPEN,
        GRIPER_CLOSE,

        MID_NODE;

    }
    public static class CreateNodes {
        protected final static HashMap<SystemsPos, GBNode> nodeMap = new HashMap<>();
        protected final static LinkedList<SystemsPos> listSystemsPos = new LinkedList<>();


        static {

            nodeMap.put(ARM_LOWWW, new NodeArm(0, 0));
            nodeMap.put(ARM_GROUND, new NodeArm(1, 0));
            nodeMap.put(ARM_MID, new NodeArm(2, 0));
            nodeMap.put(ARM_HIGH, new NodeArm(3, 0));
            listSystemsPos.add(ARM_LOWWW);
            listSystemsPos.add(ARM_GROUND);
            listSystemsPos.add(ARM_MID);
            listSystemsPos.add(ARM_HIGH);

            nodeMap.get(ARM_LOWWW).addNeighbors(new SystemsPos[]{ARM_GROUND, ARM_MID, ARM_HIGH});
            nodeMap.get(ARM_GROUND).addNeighbors(new SystemsPos[]{ARM_LOWWW, ARM_MID, ARM_HIGH});
            nodeMap.get(ARM_MID).addNeighbors(new SystemsPos[]{ARM_GROUND, ARM_LOWWW, ARM_HIGH});
            nodeMap.get(ARM_HIGH).addNeighbors(new SystemsPos[]{ARM_GROUND, ARM_MID, ARM_LOWWW});

            nodeMap.put(GRIPER_OPEN, new GriperNode());
            nodeMap.put(GRIPER_CLOSE, new GriperNode());
            listSystemsPos.add(GRIPER_OPEN);
            listSystemsPos.add(GRIPER_CLOSE);

            nodeMap.get(GRIPER_OPEN).addNeighbors(new SystemsPos[]{GRIPER_CLOSE});
            nodeMap.get(GRIPER_CLOSE).addNeighbors(new SystemsPos[]{GRIPER_OPEN});

            nodeMap.get(ARM_LOWWW).setOtherSystemMustBeToEnter(new SystemsPos[]{GRIPER_OPEN});
            nodeMap.get(ARM_LOWWW).setOtherSystemMustBeToOut(new SystemsPos[]{GRIPER_OPEN});


        }
    }
    public static class SetCosts {
        static LinkedList<Pair<String, Double>> costList = new LinkedList<>();

        static {
            costList.add(new Pair<>(ARM_LOWWW + "-" + SystemsPos.ARM_MID, 3.0));
            costList.add(new Pair<>(ARM_LOWWW + "-" + SystemsPos.ARM_HIGH, 5.0));
            costList.add(new Pair<>(ARM_LOWWW + "-" + SystemsPos.ARM_GROUND, 7.0));
            costList.add(new Pair<>(SystemsPos.ARM_GROUND + "-" + SystemsPos.ARM_HIGH, 9.0));
            costList.add(new Pair<>(SystemsPos.ARM_GROUND + "-" + SystemsPos.ARM_MID, 10.0));
            costList.add(new Pair<>(SystemsPos.ARM_MID + "-" + SystemsPos.ARM_HIGH, 12.0));
            costList.add(new Pair<>(SystemsPos.GRIPER_CLOSE + "-" + SystemsPos.GRIPER_OPEN, 15.0));
        }
    }
    public static class Constants {
        static public String systemName1 = "ARM";
        static public String systemName2 = "GRIPER";
        static public SystemsPos system1StartingNode = SystemsPos.ARM_GROUND;
        static public SystemsPos system2StartingNode = SystemsPos.GRIPER_CLOSE;
    }
}
