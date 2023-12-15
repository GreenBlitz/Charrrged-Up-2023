package edu.greenblitz.tobyDetermined.Nodesssss.Bla3;

import edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystemAlgorithm.Vertex;

import java.util.LinkedList;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemFunctions.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPos;

public class BlaBlaVertex extends Vertex {
    //TODO GET
    private SystemsPos system3Pos;

    public BlaBlaVertex(SystemsPos startPos, SystemsPos endPos, SystemsPos system2Pos, SystemsPos system3Pos) {
        super(startPos, endPos, system2Pos);
        //TODO GOOD
        if (startPos.toString().contains(systemName1)) {
            if (system2Pos.toString().contains(systemName2)) {
                this.system2Pos = system2Pos;
                this.system3Pos = system3Pos;
            } else {
                this.system3Pos = system2Pos;
                this.system2Pos = system3Pos;
            }
        }
        if (startPos.toString().contains(systemName2)) {
            if (system2Pos.toString().contains(systemName1)) {
                this.system2Pos = system2Pos;
                this.system3Pos = system3Pos;
            } else {
                this.system3Pos = system2Pos;
                this.system2Pos = system3Pos;
            }
        } else {
            if (system2Pos.toString().contains(systemName1)) {
                this.system2Pos = system2Pos;
                this.system3Pos = system3Pos;
            } else {
                this.system3Pos = system2Pos;
                this.system2Pos = system3Pos;
            }
        }
    }

    public void setSystem3Pos(SystemsPos system3Pos) {
        this.system3Pos = system3Pos;
    }

    public SystemsPos getSystem3Pos() {
        return system3Pos;
    }

    public boolean isPosFineForVertexSystem3(SystemsPos pos) {
        return getNode(startPos).getOtherSystemMustBeToOut3().contains(pos)
                &&
                getNode(endPos).getOtherSystemMustBeToEnter3().contains(pos);
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


}