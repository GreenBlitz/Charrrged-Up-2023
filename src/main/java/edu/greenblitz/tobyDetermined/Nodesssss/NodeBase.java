package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystemAlgorithm.AStarVertex;
import edu.greenblitz.tobyDetermined.Nodesssss.NonCollidingNodeSystemAlgorithm.AStar;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.TheNodes.GriperNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.TheNodes.NodeArm;
import edu.wpi.first.math.Pair;

import java.util.HashMap;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPosition.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.printPath;

public class NodeBase {
    public enum SystemsPosition {

        ARM_GROUND,
        ARM_LOW,
        ARM_MID,
        ARM_HIGH,

        GRIPER_OPEN,
        GRIPER_CLOSE,

        MID_NODE;

    }

    public static class CreateNodes {
        protected final static HashMap<SystemsPosition, GBNode> nodeMap = new HashMap<>();
        protected final static LinkedList<SystemsPosition> listSystemsPosition = new LinkedList<>();


        static {

            nodeMap.put(ARM_LOW, new NodeArm(0, 0));
            nodeMap.put(ARM_GROUND, new NodeArm(1, 0));
            nodeMap.put(ARM_MID, new NodeArm(2, 0));
            nodeMap.put(ARM_HIGH, new NodeArm(3, 0));
            listSystemsPosition.add(ARM_LOW);
            listSystemsPosition.add(ARM_GROUND);
            listSystemsPosition.add(ARM_MID);
            listSystemsPosition.add(ARM_HIGH);

            nodeMap.get(ARM_LOW).addNeighbors(new SystemsPosition[]{ARM_GROUND, ARM_MID, ARM_HIGH});
            nodeMap.get(ARM_GROUND).addNeighbors(new SystemsPosition[]{ARM_LOW, ARM_MID, ARM_HIGH});
            nodeMap.get(ARM_MID).addNeighbors(new SystemsPosition[]{ARM_GROUND, ARM_LOW, ARM_HIGH});
            nodeMap.get(ARM_HIGH).addNeighbors(new SystemsPosition[]{ARM_GROUND, ARM_MID, ARM_LOW});

            nodeMap.put(GRIPER_OPEN, new GriperNode());
            nodeMap.put(GRIPER_CLOSE, new GriperNode());
            listSystemsPosition.add(GRIPER_OPEN);
            listSystemsPosition.add(GRIPER_CLOSE);

            nodeMap.get(GRIPER_OPEN).addNeighbors(new SystemsPosition[]{GRIPER_CLOSE});
            nodeMap.get(GRIPER_CLOSE).addNeighbors(new SystemsPosition[]{GRIPER_OPEN});

            nodeMap.get(ARM_LOW).setSystem2MustBeToEnter(new SystemsPosition[]{GRIPER_OPEN});
            nodeMap.get(ARM_LOW).setSystem2MustBeToOut(new SystemsPosition[]{GRIPER_OPEN});


        }
    }

    public static class SetCosts {
        static LinkedList<Pair<String, Double>> costList = new LinkedList<>();

        static {
            costList.add(new Pair<>(ARM_LOW + "-" + ARM_MID, 3.0));
            costList.add(new Pair<>(ARM_LOW + "-" + ARM_HIGH, 5.0));
            costList.add(new Pair<>(ARM_LOW + "-" + ARM_GROUND, 7.0));
            costList.add(new Pair<>(ARM_GROUND + "-" + ARM_HIGH, 9.0));
            costList.add(new Pair<>(ARM_GROUND + "-" + ARM_MID, 10.0));
            costList.add(new Pair<>(ARM_MID + "-" + ARM_HIGH, 12.0));
            costList.add(new Pair<>(GRIPER_CLOSE + "-" + GRIPER_OPEN, 15.0));
        }
    }

    public static class Constants {
        static public String systemName1 = "ARM";
        static public String systemName2 = "GRIPER";
        static public SystemsPosition system1StartingNode = ARM_GROUND;
        static public SystemsPosition system2StartingNode = GRIPER_CLOSE;
    }

    public static class Important {
        NodeArm a = new NodeArm(0, 'r' + 'o' + 'm' + 'y' + ' ' + 'i' + 's' + ' ' + 'a' + 'u' + 't' + 'i' + 's' + 't' + 'i' + 'c');
    }

    public static class Debugger {
        public static void main(String[] args) {
            LinkedList<SystemsPosition> aStar = AStar.getPath(ARM_LOW, ARM_HIGH);
            LinkedList<SystemsPosition> aStarVertex = AStarVertex.getFinalPath(SystemsPosition.ARM_LOW, SystemsPosition.ARM_HIGH, SystemsPosition.GRIPER_CLOSE);
            //printPath(aStar);
            printPath(aStarVertex);
        }
    }

}
