package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;

import java.util.LinkedList;

public class Vertex {
    private RobotMap.Intake.SystemsPos pos1;
    private RobotMap.Intake.SystemsPos pos2;
    private RobotMap.Intake.SystemsPos otherSystem;

    public Vertex(RobotMap.Intake.SystemsPos one, RobotMap.Intake.SystemsPos two, RobotMap.Intake.SystemsPos otherSystem) {
        this.pos1=one;
        this.pos2=two;
        this.otherSystem=otherSystem;
    }

    public RobotMap.Intake.SystemsPos getPos1() {
        return pos1;
    }

    public RobotMap.Intake.SystemsPos getPos2() {
        return pos2;
    }
    public double getHOrGCostVertex(RobotMap.Intake.SystemsPos pos){
            return NodeBase.getNode(pos2).getCost(pos);
    }
    public double getFCostVertex(RobotMap.Intake.SystemsPos posS, RobotMap.Intake.SystemsPos posE){
        return getHOrGCostVertex(posS) + getHOrGCostVertex(posE);
    }
    public double getTimeCost(){
        return RobotMap.VertexCost.getCostByMap(pos1, pos2);
    }

    public RobotMap.Intake.SystemsPos getOtherSystem() {
        return otherSystem;
    }

    public void setOtherSystem(RobotMap.Intake.SystemsPos otherSystem) {
        this.otherSystem = otherSystem;
    }

    public boolean isInOtherSystemMustBe(RobotMap.Intake.SystemsPos pos){
        return (NodeBase.getNode(pos1).getOtherSystemMustBe().contains(pos) || NodeBase.getNode(pos1).getOtherSystemMustBe().isEmpty()) &&
                (NodeBase.getNode(pos2).getOtherSystemMustBe().contains(pos) || NodeBase.getNode(pos2).getOtherSystemMustBe().isEmpty());
    }
    public LinkedList<RobotMap.Intake.SystemsPos> mergeAndReturnOtherSystemMustBe(){
        LinkedList<RobotMap.Intake.SystemsPos> merge = new LinkedList<>();
        for (int i =0; i<NodeBase.getNode(pos1).getOtherSystemMustBe().size(); i++){
            if(!merge.contains(NodeBase.getNode(pos1).getOtherSystemMustBe().get(i))){
                merge.add(NodeBase.getNode(pos1).getOtherSystemMustBe().get(i));
            }
        }
        for (int i =0; i<NodeBase.getNode(pos2).getOtherSystemMustBe().size(); i++){
            if(!merge.contains(NodeBase.getNode(pos2).getOtherSystemMustBe().get(i))){
                merge.add(NodeBase.getNode(pos2).getOtherSystemMustBe().get(i));
            }
        }
        return merge;
    }
}
