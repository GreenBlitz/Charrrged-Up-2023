package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystemAlgorithm.AStarEdges;
import edu.greenblitz.tobyDetermined.Nodesssss.NonCollidingNodeSystemAlgorithm.AStar;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.TheNodes.GriperNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.TheNodes.NodeArm;
import edu.wpi.first.math.Pair;

import java.util.HashMap;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.printPath;

public class NodeBase {
    public enum SystemsState {

        ARM_GROUND,
        ARM_LOW,
        ARM_MID,
        ARM_HIGH,

        GRIPER_OPEN,
        GRIPER_CLOSE,

        MID_NODE_1,
        MID_NODE_2;

    }

    public static class CreateNodes {
        protected final static HashMap<SystemsState, GBNode> nodeMap = new HashMap<>();
        protected final static LinkedList<SystemsState> listSystemsStates = new LinkedList<>();


        static {

            nodeMap.put(ARM_LOW, new NodeArm(0, 0));
            nodeMap.put(ARM_GROUND, new NodeArm(1, 0));
            nodeMap.put(ARM_MID, new NodeArm(2, 0));
            nodeMap.put(ARM_HIGH, new NodeArm(3, 0));
            listSystemsStates.add(ARM_LOW);
            listSystemsStates.add(ARM_GROUND);
            listSystemsStates.add(ARM_MID);
            listSystemsStates.add(ARM_HIGH);

            nodeMap.get(ARM_LOW).addNeighbors(new SystemsState[]{ARM_GROUND, ARM_MID, ARM_HIGH});
            nodeMap.get(ARM_GROUND).addNeighbors(new SystemsState[]{ARM_LOW, ARM_MID, ARM_HIGH});
            nodeMap.get(ARM_MID).addNeighbors(new SystemsState[]{ARM_GROUND, ARM_LOW, ARM_HIGH});
            nodeMap.get(ARM_HIGH).addNeighbors(new SystemsState[]{ARM_GROUND, ARM_MID, ARM_LOW});

            nodeMap.put(GRIPER_OPEN, new GriperNode());
            nodeMap.put(GRIPER_CLOSE, new GriperNode());
            listSystemsStates.add(GRIPER_OPEN);
            listSystemsStates.add(GRIPER_CLOSE);

            nodeMap.get(GRIPER_OPEN).addNeighbors(new SystemsState[]{GRIPER_CLOSE});
            nodeMap.get(GRIPER_CLOSE).addNeighbors(new SystemsState[]{GRIPER_OPEN});

            nodeMap.get(ARM_LOW).setMustBeToEnter(new SystemsState[]{GRIPER_OPEN});
            nodeMap.get(ARM_LOW).setMustBeToOut(new SystemsState[]{GRIPER_OPEN});

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
        static public SystemsState system1StartingNode = ARM_GROUND;
        static public SystemsState system2StartingNode = GRIPER_CLOSE;
    }

    public static class Important {
        NodeArm a = new NodeArm(0, 'r' + 'o' + 'm' + 'y' + ' ' + 'i' + 's' + ' ' + 'a' + 'u' + 't' + 'i' + 's' + 't' + 'i' + 'c');
    }

    public static class Debugger {
        public static void main(String[] args) {
            LinkedList<SystemsState> aStar = AStar.getPath(ARM_LOW, ARM_HIGH);
            LinkedList<SystemsState> aStarVertex = AStarEdges.getFinalPath(SystemsState.ARM_LOW, SystemsState.ARM_HIGH, SystemsState.GRIPER_CLOSE);
            //printPath(aStar);
            printPath(aStarVertex);
        }
    }

}
