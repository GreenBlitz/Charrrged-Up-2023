package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;

public class ReleaseObject extends ConditionalCommand {

    public ReleaseObject(){
        super(new DropCone(), new EjectCube(),()-> Claw.getInstance().state == Claw.ClawState.CONE_MODE);
    }
}
