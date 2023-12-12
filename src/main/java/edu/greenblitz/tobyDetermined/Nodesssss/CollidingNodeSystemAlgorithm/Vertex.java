package edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystemAlgorithm;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemFunctions.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;


import java.util.LinkedList;

public class Vertex {
    private final SystemsPos pos1;
    private final SystemsPos pos2;
    private SystemsPos otherSystemPos;

    public Vertex(SystemsPos one, SystemsPos two, SystemsPos otherSystem) {
        this.pos1 = one;
        this.pos2 = two;
        this.otherSystemPos = otherSystem;
    }

    public SystemsPos getPos1() {
        return pos1;
    }

    public SystemsPos getPos2() {
        return pos2;
    }

    public double getTimeCost() {
        return getCostByMap(pos1, pos2);
    }

    public SystemsPos getOtherSystemPos() {
        return otherSystemPos;
    }

    public void setOtherSystemPos(SystemsPos otherSystem) {
        this.otherSystemPos = otherSystem;
    }

    public boolean isPosFineForVertex(SystemsPos pos) {
        return (getNode(pos1).getOtherSystemMustBeToOut().contains(pos) || getNode(pos1).getOtherSystemMustBeToOut().isEmpty()) &&
                (getNode(pos2).getOtherSystemMustBeToEnter().contains(pos) || getNode(pos2).getOtherSystemMustBeToEnter().isEmpty());
    }

    public LinkedList<SystemsPos> merge() {
        LinkedList<SystemsPos> merge = new LinkedList<>(getNode(pos1).getOtherSystemMustBeToOut());
        for (int i = 0; i < getNode(pos2).getOtherSystemMustBeToEnter().size(); i++) {
            if (!merge.contains(getNode(pos2).getOtherSystemMustBeToEnter().get(i))) {
                merge.add(getNode(pos2).getOtherSystemMustBeToEnter().get(i));
            }
        }
        return merge;
    }
}
