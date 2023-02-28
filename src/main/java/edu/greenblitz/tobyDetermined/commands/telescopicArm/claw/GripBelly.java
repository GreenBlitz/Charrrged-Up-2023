package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;

public class GripBelly extends ConditionalCommand {

    public GripBelly(){
        super(new GripCube(),new GripConeFromBelly(), ObjectSelector::IsCube);
    }
}
