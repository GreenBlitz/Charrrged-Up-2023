package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;
import static edu.greenblitz.tobyDetermined.RobotMap.NodeSystem.SystemsPos.*;
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

    public boolean inOtherSystemMustBe(SystemsPos pos) {
        return (NodeBase.getNode(pos1).getOtherSystemMustBe().contains(pos) || NodeBase.getNode(pos1).getOtherSystemMustBe().isEmpty()) &&
                (NodeBase.getNode(pos2).getOtherSystemMustBe().contains(pos) || NodeBase.getNode(pos2).getOtherSystemMustBe().isEmpty());
    }

    public LinkedList<SystemsPos> mergeAndGetOtherSystemMustBe() {
        LinkedList<SystemsPos> merge = new LinkedList<>();
        for (int i = 0; i < NodeBase.getNode(pos1).getOtherSystemMustBe().size(); i++) {
            if (!merge.contains(NodeBase.getNode(pos1).getOtherSystemMustBe().get(i))) {
                merge.add(NodeBase.getNode(pos1).getOtherSystemMustBe().get(i));
            }
        }
        for (int i = 0; i < NodeBase.getNode(pos2).getOtherSystemMustBe().size(); i++) {
            if (!merge.contains(NodeBase.getNode(pos2).getOtherSystemMustBe().get(i))) {
                merge.add(NodeBase.getNode(pos2).getOtherSystemMustBe().get(i));
            }
        }
        return merge;
    }
}
