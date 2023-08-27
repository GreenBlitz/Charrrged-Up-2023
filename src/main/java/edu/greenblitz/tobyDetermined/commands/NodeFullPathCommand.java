package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeArm;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.wpilibj2.command.ProxyCommand;

public class NodeFullPathCommand extends ProxyCommand {
    private NodeBase nodeBase;
    private NodeArm endNode;
    private final Extender extender;
    private final Elbow elbowSub;
    public NodeFullPathCommand(NodeArm endNode){
        super(new NodeToNeighbourSupplier(endNode));
        extender = Extender.getInstance();
        elbowSub = Elbow.getInstance();
        nodeBase = NodeBase.getInstance();
        this.endNode = endNode;
    }
    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        nodeBase.setCurrentNode(endNode);
        stopMotors();
    }
    public void stopMotors(){
        extender.stop();
        elbowSub.stop();
    }
}

