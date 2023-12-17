package edu.greenblitz.tobyDetermined.Nodesssss;


import java.util.Collections;
import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.systemName1;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.systemName2;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;
import static edu.greenblitz.tobyDetermined.Nodesssss.Vertexes.VertexFunctions.getAllSystemPositions;

public abstract class GBNode {
    protected boolean isNeighborsSet;
    protected LinkedList<SystemsPos> neighbors;
    protected LinkedList<SystemsPos> otherSystemMustBeToEnter2;
    protected LinkedList<SystemsPos> otherSystemMustBeToOut2;
    protected LinkedList<SystemsPos> otherSystemMustBeToEnter3;
    protected LinkedList<SystemsPos> otherSystemMustBeToOut3;
    protected String systemName;


    public GBNode(String systemName) {
        this.systemName = systemName;
        neighbors = new LinkedList<>();
        isNeighborsSet = false;
        otherSystemMustBeToEnter2 = new LinkedList<>();
        otherSystemMustBeToOut2 = new LinkedList<>();
        otherSystemMustBeToEnter3 = new LinkedList<>();
        otherSystemMustBeToOut3 = new LinkedList<>();
    }


    public void addNeighbors(SystemsPos[] neighbors) {
        if (!isNeighborsSet) {
            Collections.addAll(this.neighbors, neighbors);
            isNeighborsSet = true;
        }
    }

    public LinkedList<SystemsPos> getOtherSystemMustBeToEnter3() {
        return otherSystemMustBeToEnter3;
    }

    public LinkedList<SystemsPos> getOtherSystemMustBeToOut3() {
        return otherSystemMustBeToOut3;
    }

    public void setOtherSystemMustBeToEnter3(SystemsPos[] otherSystemMustBeToEnter3) {
        Collections.addAll(this.otherSystemMustBeToEnter3, otherSystemMustBeToEnter3);
    }

    public void setOtherSystemMustBeToOut3(SystemsPos[] otherSystemMustBeToOut3) {
        Collections.addAll(this.otherSystemMustBeToOut3, otherSystemMustBeToOut3);
    }

    public LinkedList<SystemsPos> getOtherSystemMustBeToEnter2() {
        return otherSystemMustBeToEnter2;
    }

    public LinkedList<SystemsPos> getOtherSystemMustBeToOut2() {
        return otherSystemMustBeToOut2;
    }

    public void setOtherSystemMustBeToEnter2(SystemsPos[] otherSystemMustBeToEnter2) {
        Collections.addAll(this.otherSystemMustBeToEnter2, otherSystemMustBeToEnter2);
    }

    public void setOtherSystemMustBeToOut2(SystemsPos[] otherSystemMustBeToOut2) {
        Collections.addAll(this.otherSystemMustBeToOut2, otherSystemMustBeToOut2);
    }

    public LinkedList<SystemsPos> getNeighbors() {
        return neighbors;
    }

    public LinkedList<SystemsPos> smartGetList(SystemsPos targetPos, ListType listType) {
        if (systemName.equals(systemName1)) {
            if (targetPos.toString().contains(systemName2)) {
                if (listType.equals(ListType.IN))
                    return getOtherSystemMustBeToEnter2();
                return getOtherSystemMustBeToOut2();
            }
            if (listType.equals(ListType.IN))
                return getOtherSystemMustBeToEnter3();
            return getOtherSystemMustBeToOut3();
        }
        if (targetPos.toString().contains(systemName1)) {
            if (listType.equals(ListType.IN))
                return getOtherSystemMustBeToEnter2();
            return getOtherSystemMustBeToOut2();
        }
        if (listType.equals(ListType.IN))
            return getOtherSystemMustBeToEnter3();
        return getOtherSystemMustBeToOut3();

    }

    public enum ListType {
        IN,
        OUT
    }

}
