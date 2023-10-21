package edu.greenblitz.tobyDetermined.Nodesssss;

import edu.greenblitz.tobyDetermined.RobotMap;

import static edu.greenblitz.tobyDetermined.RobotMap.Intake.GriperPos.TWO;

public class CurrentGriperNode {
    private RobotMap.Intake.GriperPos currentNode;
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
    public RobotMap.Intake.GriperPos getCurrentNode() {
        if (currentNode == null)
            currentNode = TWO;
        return currentNode;
    }
    public void setCurrentNode(RobotMap.Intake.GriperPos pos) {
        currentNode = pos;
    }
}
