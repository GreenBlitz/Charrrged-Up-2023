package edu.greenblitz.tobyDetermined.commands.telescopicArm.moveByNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState;

import edu.wpi.first.wpilibj2.command.ProxyCommand;

public class FullPath extends ProxyCommand {

    public FullPath(SystemsState endNode) {
        super(new GeneratePath(endNode));
    }

}
