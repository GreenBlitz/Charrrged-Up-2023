package edu.greenblitz.tobyDetermined.Nodesssss.CollidingNodeSystemAlgorithm;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.*;
import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsPosition;


import java.util.LinkedList;

public class Vertex {
    private final SystemsPosition startPosition;
    private final SystemsPosition endPosition;
    private SystemsPosition system2Position;

    public Vertex(SystemsPosition startPosition, SystemsPosition endPosition, SystemsPosition system2Position) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.system2Position = system2Position;
    }

    public SystemsPosition getStartPosition() {
        return startPosition;
    }

    public SystemsPosition getEndPosition() {
        return endPosition;
    }

    public double getTimeCost() {
        return getCostByMap(startPosition, endPosition);
    }

    public SystemsPosition getSystem2Position() {
        return system2Position;
    }

    public void setSystem2Position(SystemsPosition otherSystem) {
        this.system2Position = otherSystem;
    }

    public boolean isPositionFineForVertex(SystemsPosition position) {
        return (getNode(startPosition).getSystem2MustBeToOut().contains(position) || getNode(startPosition).getSystem2MustBeToOut().isEmpty()) &&
                (getNode(endPosition).getSystem2MustBeToEnter().contains(position) || getNode(endPosition).getSystem2MustBeToEnter().isEmpty());
    }

    public LinkedList<SystemsPosition> merge() {
        LinkedList<SystemsPosition> merge = new LinkedList<>(getNode(startPosition).getSystem2MustBeToOut());
        for (int i = 0; i < getNode(endPosition).getSystem2MustBeToEnter().size(); i++) {
            if (!merge.contains(getNode(endPosition).getSystem2MustBeToEnter().get(i))) {
                merge.add(getNode(endPosition).getSystem2MustBeToEnter().get(i));
            }
        }
        return merge;
    }
}
