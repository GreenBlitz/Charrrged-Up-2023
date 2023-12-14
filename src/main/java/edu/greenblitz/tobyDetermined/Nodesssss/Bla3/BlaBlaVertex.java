package edu.greenblitz.tobyDetermined.Nodesssss.Bla3;

import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemFunctions.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;

public class BlaBlaVertex {
    private final SystemsPos startPos;
    private final SystemsPos endPos;
    private SystemsPos system2Pos;
    private SystemsPos system3Pos;

    public BlaBlaVertex(SystemsPos startPos, SystemsPos endPos, SystemsPos system2Pos, SystemsPos system3Pos) {
        this.startPos = startPos;
        this.endPos = endPos;
        if (startPos.toString().contains("ARM")) {
            if (system2Pos.toString().contains("GRIPER")) {
                this.system2Pos = system2Pos;
                this.system3Pos = system3Pos;
            }
            else {
                this.system3Pos = system2Pos;
                this.system2Pos = system3Pos;
            }
        }
        if (startPos.toString().contains("GRIPER")) {
            if (system2Pos.toString().contains("ARM")) {
                this.system2Pos = system2Pos;
                this.system3Pos = system3Pos;
            }
            else {
                this.system3Pos = system2Pos;
                this.system2Pos = system3Pos;
            }
        }
        if (startPos.toString().contains("CLIMBING")) {
            if (system2Pos.toString().contains("ARM")) {
                this.system2Pos = system2Pos;
                this.system3Pos = system3Pos;
            }
            else {
                this.system3Pos = system2Pos;
                this.system2Pos = system3Pos;
            }
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

    public SystemsPos getSystem2Pos() {
        return system2Pos;
    }

    public void setSystem2Pos(SystemsPos otherSystem) {
        this.system2Pos = otherSystem;
    }

    public void setSystem3Pos(SystemsPos system3Pos) {
        this.system3Pos = system3Pos;
    }

    public SystemsPos getSystem3Pos() {
        return system3Pos;
    }

    public boolean isPosFineForVertexSys2(SystemsPos pos) {
        return getNode(startPos).getOtherSystemMustBeToOut2().contains(pos)
                &&
                getNode(endPos).getOtherSystemMustBeToEnter2().contains(pos);
    }

    public boolean isPosFineForVertexSys3(SystemsPos pos) {
        return getNode(startPos).getOtherSystemMustBeToOut3().contains(pos)
                &&
                getNode(endPos).getOtherSystemMustBeToEnter3().contains(pos);
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
    public LinkedList<SystemsPos> mergeCommonNodes3() {
        LinkedList<SystemsPos> merge = new LinkedList<>();
        for (int i = 0; i < getNode(startPos).getOtherSystemMustBeToOut3().size(); i++) {
            if (getNode(endPos).getOtherSystemMustBeToEnter3().contains(getNode(startPos).getOtherSystemMustBeToOut3().get(i))) {
                merge.add(getNode(startPos).getOtherSystemMustBeToOut3().get(i));
            }
        }
        return merge;
    }

    public boolean smartIsOnList(SystemsPos pos, SystemsPos posTarget){
        if (pos.toString().contains("ARM")) {
            if (posTarget.toString().contains("GRIPER"))
                return getNode(pos).getOtherSystemMustBeToOut2().contains(posTarget);
            return getNode(pos).getOtherSystemMustBeToOut3().contains(posTarget);
        }
        if (pos.toString().contains("GRIPER")) {
            if (posTarget.toString().contains("ARM"))
                return getNode(pos).getOtherSystemMustBeToOut2().contains(posTarget);
            return getNode(pos).getOtherSystemMustBeToOut3().contains(posTarget);
        }
        else {
            if (posTarget.toString().contains("ARM"))
                return getNode(pos).getOtherSystemMustBeToOut2().contains(posTarget);
            return getNode(pos).getOtherSystemMustBeToOut3().contains(posTarget);
        }
    }

}