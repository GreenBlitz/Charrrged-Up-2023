package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.RotatingBelly;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullGripAndPutInClaw extends SequentialCommandGroup {

    public FullGripAndPutInClaw() {
        addCommands(new FullGrip().unless(() -> RotatingBelly.getInstance().isObjectIn()));
        addCommands(new GetObjectToClaw());


    }
}
