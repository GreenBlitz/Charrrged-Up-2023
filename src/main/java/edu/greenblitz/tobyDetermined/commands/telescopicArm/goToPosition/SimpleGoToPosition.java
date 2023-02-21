package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.RotateToAngleRadians;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtendToLength;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class SimpleGoToPosition extends ParallelCommandGroup {
    protected SimpleGoToPosition(double lengthInMeters, double angleInRads) {
        addCommands(new RotateToAngleRadians(angleInRads).alongWith(new ExtendToLength(lengthInMeters)));
    }
}
