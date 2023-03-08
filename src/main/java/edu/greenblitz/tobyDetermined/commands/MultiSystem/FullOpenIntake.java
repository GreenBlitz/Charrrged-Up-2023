package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.greenblitz.tobyDetermined.commands.intake.extender.ExtendRoller;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RunRoller;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateOutDoorDirection;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FullOpenIntake extends ParallelCommandGroup {

    public FullOpenIntake() {
        super(
                new ExtendRoller(),
                new RunRoller(),
                new RotateOutDoorDirection()
        );
    }


}
