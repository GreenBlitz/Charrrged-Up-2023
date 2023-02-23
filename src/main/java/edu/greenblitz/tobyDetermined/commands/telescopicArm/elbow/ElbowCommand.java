package edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.utils.GBCommand;

public abstract class ElbowCommand extends GBCommand {

    protected Elbow elbow;

    public ElbowCommand (){
        elbow = Elbow.getInstance();
        require(elbow);
    }

    @Override
    public void initialize() {
        super.initialize();
        elbow.isUsed = true;
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        elbow.isUsed = false;
    }
}
