package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;

import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos.*;
import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos;

public class CurrentNodeArm {
    private GriperPos currentNode;
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
    public GriperPos getCurrentNode() {
        if (currentNode == null)
            currentNode = INTAKE_GRAB_CONE_POSITION;
        return currentNode;
    }
    public void setCurrentNode(GriperPos pos) {
        currentNode = pos;
    }
}
