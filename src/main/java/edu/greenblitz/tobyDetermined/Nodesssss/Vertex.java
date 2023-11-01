package edu.greenblitz.tobyDetermined.Nodesssss;

import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos;
import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem;


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
        return NodeSystem.getCostByMap(pos1, pos2);
    }

    public SystemsPos getOtherSystemPos() {
        return otherSystemPos;
    }

    public void setOtherSystemPos(SystemsPos otherSystem) {
        this.otherSystemPos = otherSystem;
    }

    public boolean isPosFineForVertex(SystemsPos pos) {
        return (NodeBase.getNode(pos1).getOtherSystemMustBeToOut().contains(pos) || NodeBase.getNode(pos1).getOtherSystemMustBeToOut().isEmpty()) &&
                (NodeBase.getNode(pos2).getOtherSystemMustBeToEnter().contains(pos) || NodeBase.getNode(pos2).getOtherSystemMustBeToEnter().isEmpty());
    }

    public LinkedList<SystemsPos> mergeAndGetPos1OutAndPos2Enter() {
        LinkedList<SystemsPos> merge = new LinkedList<>(NodeBase.getNode(pos1).getOtherSystemMustBeToOut());
        for (int i = 0; i < NodeBase.getNode(pos2).getOtherSystemMustBeToEnter().size(); i++) {
            if (!merge.contains(NodeBase.getNode(pos2).getOtherSystemMustBeToEnter().get(i))) {
                merge.add(NodeBase.getNode(pos2).getOtherSystemMustBeToEnter().get(i));
            }
        }
        return merge;
    }
}
