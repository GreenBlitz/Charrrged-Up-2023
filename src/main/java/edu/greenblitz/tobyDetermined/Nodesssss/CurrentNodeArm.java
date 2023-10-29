package edu.greenblitz.tobyDetermined.Nodesssss;

import static edu.greenblitz.tobyDetermined.RobotMap.Intake.SystemsPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.Intake.SystemsPos;

public class CurrentNodeArm {
    private SystemsPos currentNode;
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
    public SystemsPos getCurrentNode() {
        if (currentNode == null)
            currentNode = INTAKE_GRAB_CONE_POSITION;
        return currentNode;
    }
    public void setCurrentNode(SystemsPos pos) {
        currentNode = pos;
    }
}
