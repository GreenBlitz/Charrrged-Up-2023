package edu.greenblitz.tobyDetermined.Nodesssss;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.PresetPositions.*;
import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.PresetPositions;

public class CurrentNodeArm {
    private PresetPositions currentNode;
    private static CurrentNodeArm instance;
    public static CurrentNodeArm getInstance() {
        init();
        return instance;
    }

    public static void init() {
        if (instance == null) {
            instance = new CurrentNodeArm();
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
