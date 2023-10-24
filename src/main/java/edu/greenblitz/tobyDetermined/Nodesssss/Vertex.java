package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;

import java.util.LinkedList;

public class Vertex {
    private RobotMap.Intake.GriperPos pos1;
    private RobotMap.Intake.GriperPos pos2;
    private RobotMap.Intake.GriperPos otherSystem;

    public Vertex(RobotMap.Intake.GriperPos one, RobotMap.Intake.GriperPos two, RobotMap.Intake.GriperPos otherSystem) {
        this.pos1=one;
        this.pos2=two;
        this.otherSystem=otherSystem;
    }

    public RobotMap.Intake.GriperPos getPos1() {
        return pos1;
    }

    public RobotMap.Intake.GriperPos getPos2() {
        return pos2;
    }
    public double getHOrGCostVertex(RobotMap.Intake.GriperPos pos){
            return NodeBase.getNode(pos2).getCost(pos);
    }
    public double getFCostVertex(RobotMap.Intake.GriperPos posS, RobotMap.Intake.GriperPos posE){
        return getHOrGCostVertex(posS) + getHOrGCostVertex(posE);
    }

    public RobotMap.Intake.GriperPos getOtherSystem() {
        return otherSystem;
    }

    public void setOtherSystem(RobotMap.Intake.GriperPos otherSystem) {
        this.otherSystem = otherSystem;
    }

    public boolean isInOtherSystemMustBe(RobotMap.Intake.GriperPos pos){
        return (NodeBase.getNode(pos1).getOtherSystemMustBe().contains(pos) || NodeBase.getNode(pos1).getOtherSystemMustBe().isEmpty()) &&
                (NodeBase.getNode(pos2).getOtherSystemMustBe().contains(pos) || NodeBase.getNode(pos2).getOtherSystemMustBe().isEmpty());
    }
    public LinkedList<RobotMap.Intake.GriperPos> mergeAndReturnOtherSystemMustBe(){
        LinkedList<RobotMap.Intake.GriperPos> merge = new LinkedList<>();
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
