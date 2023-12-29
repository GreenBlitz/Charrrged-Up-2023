package edu.greenblitz.tobyDetermined.RobotNodeSystem;

import edu.greenblitz.utils.NodeSystemUtils.CollidingNodeSystemAlgorithm.AStarEdges;
import edu.greenblitz.utils.NodeSystemUtils.NonCollidingNodeSystemAlgorithm.AStar;
import edu.greenblitz.utils.NodeSystemUtils.TheSystemsNodes.CurrentNode;
import edu.greenblitz.utils.NodeSystemUtils.TheSystemsNodes.GBNode;
import edu.greenblitz.utils.NodeSystemUtils.TheSystemsNodes.MidNode;
import edu.greenblitz.tobyDetermined.RobotNodeSystem.TheNodes.GriperNode;
import edu.greenblitz.tobyDetermined.RobotNodeSystem.TheNodes.NodeArm;
import edu.wpi.first.math.Pair;

import java.util.HashMap;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.Constants.system1StartingNode;
import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.Constants.system2StartingNode;
import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.SystemName.*;
import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.SystemsState.*;
import static edu.greenblitz.utils.NodeSystemUtils.NodeSystemFunctions.printPath;

public class NodeBase {

    public enum SystemName {
        ARM,
        GRIPER,
        MID_NODE;
    }
    public enum SystemsState {

        GROUND(ARM),
        LOW(ARM),
        MID(ARM),
        HIGH(ARM),

        OPEN(GRIPER),
        CLOSE(GRIPER),

        MID_NODE_1(MID_NODE),
        MID_NODE_2(MID_NODE);

        public SystemName systemName;
        SystemsState(SystemName name){
            systemName = name;
        }

    }

    public static class CreateNodes {
        protected final static HashMap<SystemsState, GBNode> nodeMap = new HashMap<>();
        public final static LinkedList<SystemsState> listSystemsStates = new LinkedList<>();


        static {

            nodeMap.put(LOW, new NodeArm(0, 0));
            nodeMap.put(GROUND, new NodeArm(1, 0));
            nodeMap.put(MID, new NodeArm(2, 0));
            nodeMap.put(HIGH, new NodeArm(3, 0));
            listSystemsStates.add(LOW);
            listSystemsStates.add(GROUND);
            listSystemsStates.add(MID);
            listSystemsStates.add(HIGH);

            nodeMap.get(LOW).addNeighbors(new SystemsState[]{GROUND, MID, HIGH});
            nodeMap.get(GROUND).addNeighbors(new SystemsState[]{LOW, MID, HIGH});
            nodeMap.get(MID).addNeighbors(new SystemsState[]{GROUND, LOW, HIGH});
            nodeMap.get(HIGH).addNeighbors(new SystemsState[]{GROUND, MID, LOW});

            nodeMap.put(OPEN, new GriperNode());
            nodeMap.put(CLOSE, new GriperNode());
            listSystemsStates.add(OPEN);
            listSystemsStates.add(CLOSE);

            nodeMap.get(OPEN).addNeighbors(new SystemsState[]{CLOSE});
            nodeMap.get(CLOSE).addNeighbors(new SystemsState[]{OPEN});

            nodeMap.get(LOW).setMustBeToEnter(new SystemsState[]{OPEN});
            nodeMap.get(LOW).setMustBeToOut(new SystemsState[]{OPEN});

        }
    }

    public static class SetCosts {
        static LinkedList<Pair<String, Double>> costList = new LinkedList<>();

        static {
            costList.add(new Pair<>(LOW + "-" + MID, 3.0));
            costList.add(new Pair<>(LOW + "-" + HIGH, 5.0));
            costList.add(new Pair<>(LOW + "-" + GROUND, 7.0));
            costList.add(new Pair<>(GROUND + "-" + HIGH, 9.0));
            costList.add(new Pair<>(GROUND + "-" + MID, 10.0));
            costList.add(new Pair<>(MID + "-" + HIGH, 12.0));
            costList.add(new Pair<>(CLOSE + "-" + OPEN, 15.0));
        }
    }

    public static class Constants {
        static public SystemName systemName1 = ARM;
        static public SystemName systemName2 = GRIPER;
        static public SystemsState system1StartingNode = GROUND;
        static public SystemsState system2StartingNode = CLOSE;
    }

    public static class CreateCurrents {
        static public CurrentNode system1CurrentNode = new CurrentNode(system1StartingNode);
        static public CurrentNode system2CurrentNode = new CurrentNode(system2StartingNode);
        static public MidNode system1MidNode = new MidNode(system1StartingNode);
        static public MidNode system2MidNode = new MidNode(system2StartingNode);
    }

    public static class Debugger {
        public static void main(String[] args) {
            LinkedList<SystemsState> aStar = AStar.getPath(MID_NODE_1, HIGH);
            LinkedList<SystemsState> aStarEdges = AStarEdges.getFinalPath(MID_NODE_1, SystemsState.HIGH, SystemsState.CLOSE);
            //printPath(aStar);
            printPath(aStarEdges);
        }
    }

}
