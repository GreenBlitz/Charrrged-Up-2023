package edu.greenblitz.tobyDetermined.Nodesssss.Vertexes;

import edu.greenblitz.tobyDetermined.Nodesssss.GBNode;

import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.systemName1;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.systemName2;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.*;

public class VertexFunctions {
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

    public static void smartSetListSystemPos(SystemsPos pos, SystemsPos[] mustBe, GBNode.ListType listType) {
        if (pos.toString().contains(systemName1)) {
            if (mustBe[0].toString().contains(systemName2)) {
                setInList2(pos, listType, mustBe);
            } else {
                setInList3(pos, listType, mustBe);
            }
        }
        else if (pos.toString().contains(systemName2)) {
            if (mustBe[0].toString().contains(systemName1)) {
                setInList2(pos, listType, mustBe);
            } else {
                setInList3(pos, listType, mustBe);
            }
        } else {
            if (mustBe[0].toString().contains(systemName1)) {
                setInList2(pos, listType, mustBe);
            } else {
                setInList3(pos, listType, mustBe);
            }
        }
    }

    public static void setInList2(SystemsPos pos, GBNode.ListType type, SystemsPos[] mustBe) {
        if (type.equals(GBNode.ListType.IN))
            getNode(pos).setOtherSystemMustBeToEnter2(mustBe);
        else
            getNode(pos).setOtherSystemMustBeToOut2(mustBe);
    }
    public static void setInList3(SystemsPos pos, GBNode.ListType type, SystemsPos[] mustBe) {
        if (type.equals(GBNode.ListType.IN))
            getNode(pos).setOtherSystemMustBeToEnter3(mustBe);
        else
            getNode(pos).setOtherSystemMustBeToOut3(mustBe);
    }



    public static LinkedList<SystemsPos> getAllSystemPositions(String originalSystemName, int systemNumber){
        if (originalSystemName.equals(systemName1)){
            if (systemNumber == 2){
                return getAllSystemsPositionsByNumber(2);
            }
            return getAllSystemsPositionsByNumber(3);
        }
        if (originalSystemName.equals(systemName2)){
            if (systemNumber == 2){
                return getAllSystemsPositionsByNumber(1);
            }
            return getAllSystemsPositionsByNumber(3);
        }
        if (systemNumber == 2){
            return getAllSystemsPositionsByNumber(1);
        }
        return getAllSystemsPositionsByNumber(2);
    }
}
