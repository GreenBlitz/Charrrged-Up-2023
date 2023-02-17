package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.RotatingBelly;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullGripAndPutInClaw extends SequentialCommandGroup {

    public FullGripAndPutInClaw() {
        addCommands(new FullGrip());
        addCommands(new GetObjectToClaw());


    }
}
