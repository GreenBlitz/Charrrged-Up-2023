package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;

public class ReleaseObject extends ConditionalCommand {

    public ReleaseObject(){
        super(new DropCone(), new EjectCube(), ObjectSelector::IsCone);
    }
}
