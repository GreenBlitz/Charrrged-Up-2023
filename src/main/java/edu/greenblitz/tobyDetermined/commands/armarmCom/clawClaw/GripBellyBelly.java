package edu.greenblitz.tobyDetermined.commands.armarmCom.clawClaw;

import edu.greenblitz.tobyDetermined.subsystems.armarm.ObjectSelector;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class GripBellyBelly extends ConditionalCommand {
    public GripBellyBelly(){
        super(new GripCubeCube().raceWith(new WaitCommand(0.3)),
                new GripConeCone().raceWith(new WaitCommand(0.3)),
                ObjectSelector::IsCube);
    }
}
