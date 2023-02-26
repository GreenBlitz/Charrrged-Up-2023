package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.RotateToAngleRadians;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtendToLength;
import edu.greenblitz.tobyDetermined.subsystems.Console;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

import static edu.greenblitz.tobyDetermined.RobotMap.TelescopicArm.Extender.LENGTH_TOLERANCE;

public class FromOutGoIn extends SequentialCommandGroup {

    protected FromOutGoIn(double lengthInMeters, double angleInRads){
        addCommands(
                new ExtendToLength(
                        Math.min(RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH-LENGTH_TOLERANCE, lengthInMeters-LENGTH_TOLERANCE),true)
                        .alongWith(new RotateToAngleRadians(RobotMap.TelescopicArm.Elbow.END_WALL_ZONE_ANGLE))
        );
        addCommands(
                new RotateToAngleRadians(angleInRads,true)
                        .alongWith((new ExtendToLength(lengthInMeters,true).alongWith(new InstantCommand(()-> Console.log("passed wall zone", "yay")))
                                .beforeStarting(new WaitUntilCommand(() -> Elbow.getInstance().getState() == Elbow.ElbowState.WALL_ZONE).andThen(
                                        new WaitUntilCommand(() -> !(Elbow.getInstance().getState() == Elbow.ElbowState.WALL_ZONE))
                                )
                                )))
        );
    }
}
