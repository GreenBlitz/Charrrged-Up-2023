package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.utils.GBCommand;

public abstract class ElbowCommand extends GBCommand {

    protected Elbow elbow;

    public ElbowCommand() {
        elbow = Elbow.getInstance();
        require(elbow);
    }
}