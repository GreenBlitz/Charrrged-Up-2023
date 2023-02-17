package edu.greenblitz.tobyDetermined.commands.MultiSystem;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.intake.extender.ExtendRoller;
import edu.greenblitz.tobyDetermined.commands.intake.extender.RetractRoller;
import edu.greenblitz.tobyDetermined.commands.intake.roller.RunRoller;
import edu.greenblitz.tobyDetermined.commands.rotatingBelly.RotateOutDoorDirection;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.GoToPosition;
import edu.greenblitz.tobyDetermined.subsystems.RotatingBelly.RotatingBelly;
import edu.greenblitz.tobyDetermined.subsystems.intake.IntakeRoller;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullGrip extends SequentialCommandGroup {

    public FullGrip() {
        addCommands(
                new ParallelCommandGroup(
                        new ExtendRoller().alongWith(new RunRoller())
                                .until(() -> RotatingBelly.getInstance().isObjectIn() || IntakeRoller.getInstance().isObjectIn()),
                        new RotateOutDoorDirection())
        );

        addCommands(
                new GoToPosition(RobotMap.telescopicArm.presetPositions.INTAKE_DROP_POSITION)
                        .andThen(new RetractRoller())
        );
    }


}
