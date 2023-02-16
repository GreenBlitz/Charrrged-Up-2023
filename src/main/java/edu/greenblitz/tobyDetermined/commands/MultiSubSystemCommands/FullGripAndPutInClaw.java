package edu.greenblitz.tobyDetermined.commands.MultiSubSystemCommands;

import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateOutDoorDirection;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeGameObjectSensor;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullGripAndPutInClaw extends SequentialCommandGroup {

    public FullGripAndPutInClaw() {
        RotatingBelly.getInstance().getGameObject();

        addCommands(new FullGrip());
        addCommands(new GetObjectToClaw());


    }
}
