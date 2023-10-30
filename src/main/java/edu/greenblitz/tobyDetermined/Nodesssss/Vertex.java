package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;

import java.util.LinkedList;

public class Vertex {
    private RobotMap.Intake.SystemsPos pos1;
    private RobotMap.Intake.SystemsPos pos2;
    private RobotMap.Intake.SystemsPos otherSystemPos;

    public Vertex(RobotMap.Intake.SystemsPos one, RobotMap.Intake.SystemsPos two, RobotMap.Intake.SystemsPos otherSystem) {
        this.pos1 = one;
        this.pos2 = two;
        this.otherSystemPos = otherSystem;
    }

    public RobotMap.Intake.SystemsPos getPos1() {
        return pos1;
    }

    public RobotMap.Intake.SystemsPos getPos2() {
        return pos2;
    }

    public double getTimeCost() {
        return RobotMap.VertexCost.getCostByMap(pos1, pos2);
    }

    public RobotMap.Intake.SystemsPos getOtherSystemPos() {
        return otherSystemPos;
    }

    public void setOtherSystemPos(RobotMap.Intake.SystemsPos otherSystem) {
        this.otherSystemPos = otherSystem;
    }

    public boolean isInOtherSystemMustBe(RobotMap.Intake.SystemsPos pos) {
        return (NodeBase.getNode(pos1).getOtherSystemMustBe().contains(pos) || NodeBase.getNode(pos1).getOtherSystemMustBe().isEmpty()) &&
                (NodeBase.getNode(pos2).getOtherSystemMustBe().contains(pos) || NodeBase.getNode(pos2).getOtherSystemMustBe().isEmpty());
    }

    public LinkedList<RobotMap.Intake.SystemsPos> mergeAndReturnOtherSystemMustBe() {
        LinkedList<RobotMap.Intake.SystemsPos> merge = new LinkedList<>();
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
