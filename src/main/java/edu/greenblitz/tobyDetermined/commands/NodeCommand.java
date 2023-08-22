package edu.greenblitz.tobyDetermined.commands;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.StraightLineMotion;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;

public class NodeCommand extends GBCommand {
    private Extender extender;
    private ElbowSub elbowSub;
    private NodeBase nodeBase;
    double gamma;
    private StraightLineMotion straightLineMotion;

    int index;

    public NodeCommand(int i){
        extender = Extender.getInstance();
        elbowSub = ElbowSub.getInstance();
        straightLineMotion = StraightLineMotion.getInstance();
        nodeBase = NodeBase.getInstance();
        index = i;
        require(extender);
        require(elbowSub);
    }

    @Override
    public void execute() {
        straightLineMotion.moveArm(3, 2, index, gamma);
    }

    @Override
    public boolean isFinished() {
        return nodeBase.getIfInNode(elbowSub.getAngleRadians(), index);
    }

    @Override
    public void end(boolean interrupted) {
        straightLineMotion.resetMotors();
    }
}
