package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.Nodes.ClimbingNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.Nodes.GriperNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.MidNodes.MidNodeSystem1;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.Nodes.NodeArm;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.wpi.first.math.Pair;

import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SetCosts.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.CreateNodes.*;


public class NodeSystemUtils {

    //TODO GOOD , not good{
    public static GBNode getNodeBySystemName(SystemsPos start) {
        if (start.toString().contains(systemName1))
            return new NodeArm(Extender.getInstance().getLength(), Elbow.getInstance().getAngleRadians());
        else if (start.toString().contains(systemName2))
            return new GriperNode();
        return new ClimbingNode();
    }



    //TODO GENERIC FOR ALL ALGORITHMS{

    //todo addToOpen(){}

    public static <T> boolean isNotInList(T object, LinkedList<T> list) {
        return !list.contains(object);
    }

    public static <T> void printPath(LinkedList<T> pathList) {
        for (T t : pathList) {
            System.out.print(t + ", ");
        }
        System.out.println();
    }

    //todo generic for all algorithms}

    public static double getCostByMap(SystemsPos a, SystemsPos b) {
        for (Pair<String, Double> stringDoublePair : costList) {
            if (stringDoublePair.getFirst().contains(a.toString()) && stringDoublePair.getFirst().contains(b.toString()))
                return stringDoublePair.getSecond();
        }
        return 0;
    }

    public static GBNode getNode(SystemsPos specificNode) {
        if (specificNode.equals(MID_NODE))
            return MidNodeSystem1.getInstance().getMidNode();
        return nodeMap.get(specificNode);
    }

    public static LinkedList<SystemsPos> getAllSystemsPositionsByNumber(int systemNumber) {
        return new LinkedList<>(listSystemsPos.get(systemNumber-1));
    }

    public static LinkedList<SystemsPos> getAllSystemPositionsByPos(SystemsPos secondSystem){
        if (secondSystem.toString().contains(systemName1))
            return new LinkedList<>(listSystemsPos.get(0));
        if (secondSystem.toString().contains(systemName2))
            return new LinkedList<>(listSystemsPos.get(1));
        return new LinkedList<>(listSystemsPos.get(2));
    }


}
