package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.greenblitz.tobyDetermined.commands.telescopicArm.claw.*;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.*;

public class GripFromBelly extends SequentialCommandGroup {
    public GripFromBelly(){
        super(
                new ConditionalCommand(
                        new FullGripCone(),
                        new FullGripCube(),
                        ObjectSelector::IsCone)


        );


    }
}
