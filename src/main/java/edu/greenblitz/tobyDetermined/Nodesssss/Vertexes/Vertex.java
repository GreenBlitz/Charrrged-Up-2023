package edu.greenblitz.tobyDetermined.Nodesssss.Vertexes;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;

import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.systemName1;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.systemName2;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.*;

public class Vertex {
    protected final SystemsPos startPos;
    protected final SystemsPos endPos;
    protected SystemsPos system2Pos;
    private SystemsPos system3Pos;

    public Vertex(SystemsPos start, NodeBase.SystemsPos end, SystemsPos system2Pos, SystemsPos system3Pos) {
        this.startPos = start;
        this.endPos = end;
        if (startPos.toString().contains(systemName1)) {
            if (system2Pos.toString().contains(systemName2)) {
                this.system2Pos = system2Pos;
                this.system3Pos = system3Pos;
            } else {
                this.system3Pos = system2Pos;
                this.system2Pos = system3Pos;
            }
        }
        if (system2Pos.toString().contains(systemName1)) {
            this.system2Pos = system2Pos;
            this.system3Pos = system3Pos;
        } else {
            this.system3Pos = system2Pos;
            this.system2Pos = system3Pos;
        }
    }

    public Vertex(SystemsPos start, SystemsPos end, SystemsPos system2Pos) {
        startPos = start;
        this.endPos = end;
        if (startPos.toString().contains(systemName1)) {
            if (system2Pos.toString().contains(systemName2)) {
                this.system2Pos = system2Pos;
            } else {
                this.system3Pos = system2Pos;
            }
        }
        if (system2Pos.toString().contains(systemName1)) {
            this.system2Pos = system2Pos;
        } else {
            this.system3Pos = system2Pos;
        }
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


    public void smartSetSystemPos(SystemsPos targetPos){
        if(startPos.toString().contains(systemName1)){
            if (targetPos.toString().contains(systemName2))
                setSystem2Pos(targetPos);
            setSystem3Pos(targetPos);
        }
        if (targetPos.toString().contains(systemName1))
            setSystem2Pos(targetPos);
        setSystem3Pos(targetPos);
    }

    private void setSystem2Pos(SystemsPos otherSystem) {
        this.system2Pos = otherSystem;
    }

    private void setSystem3Pos(SystemsPos system3Pos) {
        this.system3Pos = system3Pos;
    }

    public SystemsPos smartGetSystemPos(SystemsPos targetPos){
        if(startPos.toString().contains(systemName1)){
            if (targetPos.toString().contains(systemName2))
                return getSystem2Pos();
            return getSystem3Pos();
        }
        if (targetPos.toString().contains(systemName1))
            return getSystem2Pos();
        return getSystem3Pos();
    }
    private SystemsPos getSystem3Pos() {
        return system3Pos;
    }
    private SystemsPos getSystem2Pos() {
        return system2Pos;
    }

    public LinkedList<SystemsPos> smartMergeCommonNodes(SystemsPos targetPos){
        if(startPos.toString().contains(systemName1)){
            if (targetPos.toString().contains(systemName2))
                return mergeCommonNodes2();
            return mergeCommonNodes3();
        }
        if (targetPos.toString().contains(systemName1))
            return mergeCommonNodes2();
        return mergeCommonNodes3();
    }

    private LinkedList<SystemsPos> mergeCommonNodes3() {
        LinkedList<SystemsPos> merge = new LinkedList<>();
        for (int i = 0; i < getNode(startPos).getOtherSystemMustBeToOut3().size(); i++) {
            if (getNode(endPos).getOtherSystemMustBeToEnter3().contains(getNode(startPos).getOtherSystemMustBeToOut3().get(i))) {
                merge.add(getNode(startPos).getOtherSystemMustBeToOut3().get(i));
            }
        }
        return merge;
    }

    private LinkedList<SystemsPos> mergeCommonNodes2() {
        LinkedList<SystemsPos> merge = new LinkedList<>();
        for (int i = 0; i < getNode(startPos).getOtherSystemMustBeToOut2().size(); i++) {
            if (getNode(endPos).getOtherSystemMustBeToEnter2().contains(getNode(startPos).getOtherSystemMustBeToOut2().get(i))) {
                merge.add(getNode(startPos).getOtherSystemMustBeToOut2().get(i));
            }
        }
        return merge;
    }

   public boolean smartIsPosFineForVertex(SystemsPos pos){
        if (startPos.toString().contains(systemName1)){
            if (pos.toString().contains(systemName2))
                return isPosFineForVertexSystem2(pos);
            return isPosFineForVertexSystem3(pos);
        }
       if (pos.toString().contains(systemName1))
            return isPosFineForVertexSystem2(pos);
        return isPosFineForVertexSystem3(pos);
   }

    private boolean isPosFineForVertexSystem2(SystemsPos pos) {
        //System.out.println(getNode(startPos).getOtherSystemMustBeToOut2().contains(pos));
        //System.out.println(getNode(endPos).getOtherSystemMustBeToEnter2().contains(pos));
        //printPath(getNode(startPos).getOtherSystemMustBeToOut2());
        //System.out.println(endPos);
        //printPath(getNode(endPos).getOtherSystemMustBeToEnter2());
        return getNode(startPos).getOtherSystemMustBeToOut2().contains(pos)
                &&
                getNode(endPos).getOtherSystemMustBeToEnter2().contains(pos);
    }

    private boolean isPosFineForVertexSystem3(SystemsPos pos) {
        return getNode(startPos).getOtherSystemMustBeToOut3().contains(pos)
                &&
                getNode(endPos).getOtherSystemMustBeToEnter3().contains(pos);
    }
}


