package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;

import java.util.LinkedList;

public class Vertex {
    private RobotMap.Intake.GriperPos pos1;
    private RobotMap.Intake.GriperPos pos2;

    public Vertex(RobotMap.Intake.GriperPos one, RobotMap.Intake.GriperPos two) {
        this.pos1=one;
        this.pos2=two;
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
    public boolean ifInOtherSystemMustBe(RobotMap.Intake.GriperPos pos){
        return NodeBase.getNode(pos1).getOtherSystemMustBe().contains(pos) || NodeBase.getNode(pos2).getOtherSystemMustBe().contains(pos);
    }
    public LinkedList<RobotMap.Intake.GriperPos> mergeAndReturnOtherSystemMustBe(){
        LinkedList<RobotMap.Intake.GriperPos> merge = new LinkedList<>();
        merge.addAll(NodeBase.getNode(pos1).getOtherSystemMustBe());
        merge.addAll(NodeBase.getNode(pos2).getOtherSystemMustBe());
        return merge;
    }
    public static void main(String[] args){
        Vertex a = new Vertex(RobotMap.Intake.GriperPos.LOW, RobotMap.Intake.GriperPos.CONE_HIGH);
        System.out.println(a.getHOrGCostVertex(RobotMap.Intake.GriperPos.CONE_HIGH));
    }


}
