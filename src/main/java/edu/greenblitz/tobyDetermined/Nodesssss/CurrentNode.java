package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.PresetPositions.*;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.PresetPositions;

public class CurrentNode {
    private PresetPositions currentNode;
    private static CurrentNode instance;
    public static CurrentNode getInstance() {
        init();
        return instance;
    }

    public static void init() {
        if (instance == null) {
            instance = new CurrentNode();
        }
    }
    public PresetPositions getCurrentNode() {
        if (currentNode == null)
            currentNode = INTAKE_GRAB_CONE_POSITION;
        return currentNode;
    }
    public void setCurrentNode(PresetPositions pos) {
        currentNode = pos;
    }
}
