package edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystemAlgorithm;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemFunctions.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;


import java.util.LinkedList;

public class Vertex {
    private final SystemsPos pos1;
    private final SystemsPos pos2;
    private SystemsPos otherSystemPos1;

    public Vertex(SystemsPos one, SystemsPos two, SystemsPos otherSystem1) {
        this.pos1 = one;
        this.pos2 = two;
        this.otherSystemPos1 = otherSystem1;
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

    public SystemsPos getOtherSystemPos1() {
        return otherSystemPos1;
    }

    public void setOtherSystemPos1(SystemsPos otherSystem) {
        this.otherSystemPos1 = otherSystem;
    }

    public boolean isPosFineForVertex(SystemsPos pos) {
        return getNode(pos1).getOtherSystemMustBeToOut2().contains(pos)
                &&
                getNode(pos2).getOtherSystemMustBeToEnter2().contains(pos);
    }

    public LinkedList<SystemsPos> mergeCommonNodes() {
        LinkedList<SystemsPos> merge = new LinkedList<>();
        for (int i = 0; i < getNode(pos1).getOtherSystemMustBeToOut2().size(); i++) {
            if (getNode(pos2).getOtherSystemMustBeToEnter2().contains(getNode(pos1).getOtherSystemMustBeToOut2().get(i))) {
                merge.add(getNode(pos1).getOtherSystemMustBeToOut2().get(i));
            }
        }
        return merge;
    }
}
