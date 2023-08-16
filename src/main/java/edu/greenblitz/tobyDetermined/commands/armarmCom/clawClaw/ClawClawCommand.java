package edu.greenblitz.tobyDetermined.commands.armarmCom.clawClaw;

import edu.greenblitz.tobyDetermined.subsystems.armarm.ClawSub;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.greenblitz.utils.GBCommand;

import static scala.Predef.require;

public class ClawClawCommand extends GBCommand {
    protected ClawSub claw;
    public ClawClawCommand (){
        claw = ClawSub.getInstance();
        require(claw);
    }
}
