package edu.greenblitz.tobyDetermined.commands.telescopicArm;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.RotateToAngle;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtendToLength;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class GoToPosition extends SequentialCommandGroup {

    public GoToPosition(double lengthInMeters, double angleInRads) {
        if (edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.getHypotheticalState(lengthInMeters) == edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender.ExtenderState.IN_WALL_LENGTH || edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.getInstance().isInTheSameState(angleInRads)) {
            addCommands(new RotateToAngle(angleInRads).alongWith(new ExtendToLength(lengthInMeters)));
        }
        /*
           if the desired position needs to pass through the entrance zone and the extension to long the movement is split to multiple parts:
           shrink to passing length,
           rotate arm
           extend to wanted length
          */
        else {
            if (edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.getInstance().state == edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.ElbowState.OUT_ROBOT && edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.getHypotheticalState(angleInRads) == edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.ElbowState.IN_BELLY) {
                addCommands(
                        new ExtendToLength(
                                Math.min(RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH, lengthInMeters))
                                .alongWith(new RotateToAngle(RobotMap.TelescopicArm.Elbow.END_WALL_ZONE_ANGLE))
                );
                addCommands(new RotateToAngle(RobotMap.TelescopicArm.Elbow.STARTING_WALL_ZONE_ANGLE)
                        .andThen(
                                new RotateToAngle(angleInRads)
                                        .andThen(new ExtendToLength(lengthInMeters))
                        )
                );
            } else if (edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.getInstance().state == edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.ElbowState.IN_BELLY && edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.getHypotheticalState(angleInRads) == edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow.ElbowState.OUT_ROBOT) {
                addCommands(
                        new ExtendToLength(
                                Math.min(RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH, lengthInMeters))
                                .alongWith(new RotateToAngle(RobotMap.TelescopicArm.Elbow.STARTING_WALL_ZONE_ANGLE))
                );
                addCommands(new RotateToAngle(RobotMap.TelescopicArm.Elbow.END_WALL_ZONE_ANGLE)
                        .andThen(
                                new RotateToAngle(angleInRads)
                                        .andThen(new ExtendToLength(lengthInMeters))
                        )
                );
            }
        }
    }

    public GoToPosition(RobotMap.TelescopicArm.PresetPositions position) {
        this(position.distance, position.angleInRadians);
    }


}
