package edu.greenblitz.utils.NodeSystemUtils.CollidingNodeSystemAlgorithm;

import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeSystemDependentFunctions.getCostByMap;
import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeSystemDependentFunctions.getNode;
import static edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.SystemsState;


import java.util.LinkedList;

public class Edge {
    private final SystemsState startState;
    private final SystemsState endState;
    private SystemsState system2State;

    public Edge(SystemsState startState, SystemsState endState, SystemsState system2State) {
        this.startState = startState;
        this.endState = endState;
        this.system2State = system2State;
    }

    public SystemsState getStartState() {
        return startState;
    }

    public SystemsState getEndState() {
        return endState;
    }

    public double getTimeCost() {
        return getCostByMap(startState, endState);
    }

    public SystemsState getSystem2State() {
        return system2State;
    }

    public void setSystem2State(SystemsState otherSystem) {
        this.system2State = otherSystem;
    }

    public boolean isValidForEdge(SystemsState state) {
        return (getNode(startState).getSystem2MustBeToExitState().contains(state) || getNode(startState).getSystem2MustBeToExitState().isEmpty()) &&
                (getNode(endState).getSystem2MustBeToEnterState().contains(state) || getNode(endState).getSystem2MustBeToEnterState().isEmpty());
    }

    public LinkedList<SystemsState> getReunion() {
        if (getNode(endState).getSystem2MustBeToEnterState().isEmpty())
            return new LinkedList<>(getNode(startState).getSystem2MustBeToExitState());
        if (getNode(startState).getSystem2MustBeToExitState().isEmpty())
            return new LinkedList<>(getNode(endState).getSystem2MustBeToEnterState());
        LinkedList<SystemsState> reunion = new LinkedList<>();
        for (int i = 0; i < getNode(endState).getSystem2MustBeToEnterState().size(); i++) {
            if (getNode(startState).getSystem2MustBeToEnterState().contains(getNode(endState).getSystem2MustBeToEnterState().get(i))) {
                reunion.add(getNode(endState).getSystem2MustBeToEnterState().get(i));
            }
        }
        return reunion;
    }
}
