package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.ClimbingNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.GriperNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.NodeArm;
import edu.wpi.first.math.Pair;
import java.util.HashMap;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos.*;

public class NodeBase {
    public enum SystemsPos {
        GRIPER_CLOSE,
        GRIPER_OPEN,

        CLIMBING_ROMY,
        CLIMBING_NOAM,

        ARM_LOW,
        ARM_MID,
        ARM_HIGH,
        ARM_ZG,

        MID_NODE;

    }
    public static class CreateNodes {
        protected final static HashMap<SystemsPos, GBNode> nodeMap = new HashMap<>();
        protected final static LinkedList<SystemsPos> listSystemsPos = new LinkedList<>();


        static {
            nodeMap.put(CLIMBING_ROMY, new ClimbingNode());
            listSystemsPos.add(CLIMBING_ROMY);
            nodeMap.put(CLIMBING_NOAM, new ClimbingNode());
            listSystemsPos.add(CLIMBING_NOAM);

            nodeMap.get(CLIMBING_NOAM).addNeighbors(new SystemsPos[]{CLIMBING_ROMY});
            nodeMap.get(CLIMBING_ROMY).addNeighbors(new SystemsPos[]{CLIMBING_NOAM});


            nodeMap.put(ARM_LOW, new NodeArm(0, 0));
            nodeMap.put(ARM_MID, new NodeArm(1, 0));
            nodeMap.put(ARM_HIGH, new NodeArm(2, 0));
            nodeMap.put(ARM_ZG, new NodeArm(3, 0));
            listSystemsPos.add(ARM_LOW);
            listSystemsPos.add(ARM_MID);
            listSystemsPos.add(ARM_HIGH);
            listSystemsPos.add(ARM_ZG);

            nodeMap.get(ARM_LOW).addNeighbors(new SystemsPos[]{ARM_ZG, ARM_MID, ARM_HIGH});
            nodeMap.get(ARM_MID).addNeighbors(new SystemsPos[]{ARM_ZG, ARM_LOW, ARM_HIGH});
            nodeMap.get(ARM_HIGH).addNeighbors(new SystemsPos[]{ARM_ZG, ARM_MID, ARM_LOW});
            nodeMap.get(ARM_ZG).addNeighbors(new SystemsPos[]{ ARM_HIGH, ARM_MID, ARM_LOW});


            nodeMap.put(GRIPER_OPEN, new GriperNode());
            nodeMap.put(GRIPER_CLOSE, new GriperNode());
            listSystemsPos.add(GRIPER_OPEN);
            listSystemsPos.add(GRIPER_CLOSE);

            nodeMap.get(GRIPER_OPEN).addNeighbors(new SystemsPos[]{GRIPER_CLOSE});
            nodeMap.get(GRIPER_CLOSE).addNeighbors(new SystemsPos[]{GRIPER_OPEN});


            nodeMap.get(GRIPER_OPEN).setOtherSystemMustBeToEnter1(new SystemsPos[]{ARM_ZG,MID_NODE,ARM_HIGH});
            nodeMap.get(GRIPER_OPEN).setOtherSystemMustBeToOut1(new SystemsPos[]{ARM_ZG,MID_NODE,ARM_HIGH});
            nodeMap.get(GRIPER_CLOSE).setOtherSystemMustBeToEnter1(new SystemsPos[]{ARM_ZG,MID_NODE,ARM_HIGH});
            nodeMap.get(GRIPER_CLOSE).setOtherSystemMustBeToOut1(new SystemsPos[]{ARM_ZG,MID_NODE,ARM_HIGH});

            nodeMap.get(CLIMBING_ROMY).setOtherSystemMustBeToEnter1(new SystemsPos[]{ARM_ZG});
            nodeMap.get(CLIMBING_NOAM).setOtherSystemMustBeToEnter1(new SystemsPos[]{ARM_ZG});

            nodeMap.get(ARM_LOW).setOtherSystemMustBeToEnter1(new SystemsPos[]{GRIPER_OPEN});
            nodeMap.get(ARM_LOW).setOtherSystemMustBeToOut1(new SystemsPos[]{GRIPER_OPEN});
            nodeMap.get(ARM_LOW).setOtherSystemMustBeToEnter2(new SystemsPos[]{CLIMBING_ROMY});
            nodeMap.get(ARM_LOW).setOtherSystemMustBeToOut2(new SystemsPos[]{CLIMBING_ROMY});


        }
    }
    public static class SetCosts {
        static LinkedList<Pair<String, Double>> costList = new LinkedList<>();

        static {
            costList.add(new Pair<>(ARM_LOW + "-" + ARM_MID, 3.0));
            costList.add(new Pair<>(ARM_LOW + "-" + ARM_HIGH, 2.0));
            costList.add(new Pair<>(ARM_LOW + "-" + ARM_ZG, 7.0));
            costList.add(new Pair<>(ARM_HIGH + "-" + ARM_ZG, 9.0));
            costList.add(new Pair<>(ARM_HIGH + "-" + ARM_MID, 1.0));
            costList.add(new Pair<>(ARM_ZG + "-" + ARM_MID, 2.0));
            costList.add(new Pair<>(GRIPER_CLOSE + "-" + GRIPER_OPEN, 15.0));
            costList.add(new Pair<>(CLIMBING_NOAM + "-" + CLIMBING_ROMY, 17.0));
        }
    }
    public static class Constants {
        static public String systemName1 = "ARM";
        static public String systemName2 = "GRIPER";
        static public String systemName3 = "CLIMBING";
        static public SystemsPos system1StartingNode = ARM_ZG;
        static public SystemsPos system2StartingNode = GRIPER_CLOSE;
        static public SystemsPos system3StartingNode = CLIMBING_ROMY;
    }
}
