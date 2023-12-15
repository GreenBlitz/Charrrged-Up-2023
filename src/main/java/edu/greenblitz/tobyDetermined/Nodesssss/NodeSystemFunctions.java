package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.Nodesssss.Bla3.BlaBla;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.ClimbingNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.GriperNode;
import edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.NodeArm;
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
                if (listType.equals(GBNode.ListType.IN))
                    getNode(pos).setOtherSystemMustBeToEnter2(mustBe);
                else
                    getNode(pos).setOtherSystemMustBeToOut2(mustBe);
            }
            else {
                if (listType.equals(GBNode.ListType.IN))
                    getNode(pos).setOtherSystemMustBeToEnter3(mustBe);
                else
                    getNode(pos).setOtherSystemMustBeToOut3(mustBe);
            }
        }
        if (pos.toString().contains(systemName2)) {
            if (mustBe[0].toString().contains(systemName1)) {
                if (listType.equals(GBNode.ListType.IN))
                    getNode(pos).setOtherSystemMustBeToEnter2(mustBe);
                else
                    getNode(pos).setOtherSystemMustBeToOut2(mustBe);
            }
            else {
                if (listType.equals(GBNode.ListType.IN))
                    getNode(pos).setOtherSystemMustBeToEnter3(mustBe);
                else
                    getNode(pos).setOtherSystemMustBeToOut3(mustBe);
            }
        }
        else {
            if (mustBe[0].toString().contains(systemName1)) {
                if (listType.equals(GBNode.ListType.IN))
                    getNode(pos).setOtherSystemMustBeToEnter2(mustBe);
                else
                    getNode(pos).setOtherSystemMustBeToOut2(mustBe);
            }
            else {
                if (listType.equals(GBNode.ListType.IN))
                    getNode(pos).setOtherSystemMustBeToEnter3(mustBe);
                else
                    getNode(pos).setOtherSystemMustBeToOut3(mustBe);
            }
        }
    }



    public static double getCostByMap(SystemsPos a, SystemsPos b) {
        for (Pair<String, Double> stringDoublePair : costList) {
            if (stringDoublePair.getFirst().contains(a.toString()) && stringDoublePair.getFirst().contains(b.toString()))
                return stringDoublePair.getSecond();
        }
        return 0;
    }

    public static GBNode getNode(SystemsPos specificNode) {
        if (specificNode.equals(MID_NODE))
            return MidNode.getInstance().getGBNode();
        return nodeMap.get(specificNode);
    }

    public static LinkedList<SystemsPos> getAllSystemsPositions() {
        return new LinkedList<>(listSystemsPos);
    }

}
