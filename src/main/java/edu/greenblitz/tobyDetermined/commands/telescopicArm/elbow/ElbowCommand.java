package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSub;
import edu.greenblitz.utils.GBCommand;

public abstract class ElbowCommand extends GBCommand {

    protected ElbowSub elbow;

    public ElbowCommand() {
        elbow = ElbowSub.getInstance();
        require(elbow);
    }
}