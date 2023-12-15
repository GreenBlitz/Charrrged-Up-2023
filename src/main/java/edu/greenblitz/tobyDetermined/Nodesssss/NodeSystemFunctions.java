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


public class NodeSystemFunctions {

    //TODO GOOD , not good{
    public static GBNode getNodeBySystemName(SystemsPos start) {
        if (start.toString().contains(systemName1))
            return new NodeArm(Extender.getInstance().getLength(), Elbow.getInstance().getAngleRadians());
        else if (start.toString().contains(systemName2))
            return new GriperNode();
        return new ClimbingNode();
    }

    public static boolean smartIsOnList(SystemsPos pos, SystemsPos posTarget) {
        if (pos.toString().contains(systemName1)) {
            if (posTarget.toString().contains(systemName2))
                return getNode(pos).getOtherSystemMustBeToOut2().contains(posTarget);
            return getNode(pos).getOtherSystemMustBeToOut3().contains(posTarget);
        }
        if (pos.toString().contains(systemName2)) {
            if (posTarget.toString().contains(systemName1))
                return getNode(pos).getOtherSystemMustBeToOut2().contains(posTarget);
            return getNode(pos).getOtherSystemMustBeToOut3().contains(posTarget);
        } else {
            if (posTarget.toString().contains(systemName1))
                return getNode(pos).getOtherSystemMustBeToOut2().contains(posTarget);
            return getNode(pos).getOtherSystemMustBeToOut3().contains(posTarget);
        }
    }

    public static void smartSetList(SystemsPos pos, SystemsPos[] mustBe, GBNode.ListType listType) {
        if (pos.toString().contains(systemName1)) {
            if (mustBe[0].toString().contains(systemName2)) {
                setInList(pos, listType, mustBe);
            } else {
                setInList(pos, listType, mustBe);
            }
        }
        else if (pos.toString().contains(systemName2)) {
            if (mustBe[0].toString().contains(systemName1)) {
                setInList(pos, listType, mustBe);
            } else {
                setInList(pos, listType, mustBe);
            }
        } else {
            if (mustBe[0].toString().contains(systemName1)) {
                setInList(pos, listType, mustBe);
            } else {
                setInList(pos, listType, mustBe);
            }
        }
    }
    public static void setInList(SystemsPos pos, GBNode.ListType type, SystemsPos[] mustBe) {
        if (type.equals(GBNode.ListType.IN))
            getNode(pos).setOtherSystemMustBeToEnter2(mustBe);
        else
            getNode(pos).setOtherSystemMustBeToOut2(mustBe);
    }
    //todo good}


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

    public static LinkedList<SystemsPos> getAllSystemsPositions() {
        return new LinkedList<>(listSystemsPos);
    }

}
