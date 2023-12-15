package edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystemAlgorithm;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemFunctions.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;


import java.util.LinkedList;

public class Vertex {
    protected final SystemsPos startPos;
    protected final SystemsPos endPos;
    protected SystemsPos system2Pos;

    public Vertex(SystemsPos one, SystemsPos two, SystemsPos system2Pos) {
        this.startPos = one;
        this.endPos = two;
        this.system2Pos = system2Pos;
    }

    public SystemsPos getStartPos() {
        return startPos;
    }

    public SystemsPos getEndPos() {
        return endPos;
    }

    public double getTimeCost() {
        return getCostByMap(startPos, endPos);
    }

    public SystemsPos getSystem2Pos() {
        return system2Pos;
    }

    public void setSystem2Pos(SystemsPos otherSystem) {
        this.system2Pos = otherSystem;
    }

    public boolean isPosFineForVertexSystem2(SystemsPos pos) {
        return getNode(startPos).getOtherSystemMustBeToOut2().contains(pos)
                &&
                getNode(endPos).getOtherSystemMustBeToEnter2().contains(pos);
    }

    public LinkedList<SystemsPos> mergeCommonNodes2() {
        LinkedList<SystemsPos> merge = new LinkedList<>();
        for (int i = 0; i < getNode(startPos).getOtherSystemMustBeToOut2().size(); i++) {
            if (getNode(endPos).getOtherSystemMustBeToEnter2().contains(getNode(startPos).getOtherSystemMustBeToOut2().get(i))) {
                merge.add(getNode(startPos).getOtherSystemMustBeToOut2().get(i));
            }
        }
        return merge;
    }
}
