package edu.greenblitz.tobyDetermined.commands.TelescopicArm;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.greenblitz.utils.GBCommand;

public class ClawCommand extends GBCommand {

    protected Claw claw;
    public ClawCommand (){
        claw = Claw.getInstance();
        require(claw);
    }
}
