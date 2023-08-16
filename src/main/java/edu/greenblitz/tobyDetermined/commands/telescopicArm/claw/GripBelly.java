package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class GripBelly extends ConditionalCommand {

    public GripBelly(){
        super(new GripCube().raceWith(new WaitCommand(0.3).andThen(new DefaultRotateWhenCube())),
                new GripCone().raceWith(new WaitCommand(0.3)),
                ObjectSelector::IsCube);
    }
}
