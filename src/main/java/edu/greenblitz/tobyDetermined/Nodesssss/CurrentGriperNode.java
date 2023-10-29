package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;

import static edu.greenblitz.tobyDetermined.RobotMap.Intake.SystemsPos.*;

public class CurrentGriperNode {
    private RobotMap.Intake.SystemsPos currentNode;
    private static CurrentGriperNode instance;
    public static CurrentGriperNode getInstance() {
        init();
        return instance;
    }

    public static void init() {
        if (instance == null) {
            instance = new CurrentGriperNode();
        }
    }
    public RobotMap.Intake.SystemsPos getCurrentNode() {
        if (currentNode == null)
            currentNode = GRIPER_TWO;
        return currentNode;
    }
    public void setCurrentNode(RobotMap.Intake.SystemsPos pos) {
        currentNode = pos;
    }
}
