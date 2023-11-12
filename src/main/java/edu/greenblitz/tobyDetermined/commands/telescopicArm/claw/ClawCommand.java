package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw.Claw;
import edu.greenblitz.utils.GBCommand;

public abstract class ClawCommand extends GBCommand {

    protected Claw claw;
    public ClawCommand (){
        claw = Claw.getInstance();
        require(claw);
    }
}
