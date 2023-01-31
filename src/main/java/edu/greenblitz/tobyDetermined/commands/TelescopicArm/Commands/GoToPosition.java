package edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands;

import edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands.Elbow.RotateToAngle;
import edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands.Extender.ExtendToLength;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands.armUtils.isInTheSameSide;

public class GoToPosition extends SequentialCommandGroup {

    public GoToPosition (double lengthInMeters, double angleInRads){

        addCommands(new RotateToAngle(angleInRads));
        addCommands(new ExtendToLength(lengthInMeters));
    }
}
