package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.CurrentNode;
import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.Extender;
import edu.wpi.first.wpilibj2.command.ProxyCommand;

public class NodeFullTrack extends ProxyCommand {
    private final Extender extender;
    private final Elbow elbow;
    private RobotMap.TelescopicArm.PresetPositions endNode;

    public NodeFullTrack(RobotMap.TelescopicArm.PresetPositions endNode){
        super(new NodeTracSupplier(endNode));

        this.endNode = endNode;

        extender = Extender.getInstance();
        elbow = Elbow.getInstance();
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        CurrentNode.setCurrentNode(endNode);
        stopMotors();//needs to be changed when default command works
    }
    public void stopMotors(){
        extender.stop();
        elbow.stop();
    }
}
