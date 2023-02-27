package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullIntakeAndPutInClaw extends SequentialCommandGroup {

    public FullIntakeAndPutInClaw() {
        addCommands(new FullIntake(), new GetObjectToClaw());
    }
}
