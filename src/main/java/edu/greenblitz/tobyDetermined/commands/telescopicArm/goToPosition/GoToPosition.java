package edu.greenblitz.tobyDetermined.commands.telescopicArm.goToPosition;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class GoToPosition extends SequentialCommandGroup {

    public GoToPosition(double lengthInMeters, double angleInRads) {
        /*
           if the desired position needs to pass through the entrance zone and the extension to long the movement is split to multiple parts:
           shrink to passing length,
           rotate arm
           extend to wanted length
          */
        addCommands(new SimpleGoToPosition(lengthInMeters, angleInRads)
                .unless(() -> !(Extender.getHypotheticalState(lengthInMeters) == Extender.ExtenderState.IN_WALL_LENGTH || Elbow.getInstance().isInTheSameState(angleInRads))));
        addCommands(new FromOutGoIn(lengthInMeters, angleInRads)
                .unless(() -> !(Elbow.getInstance().state == Elbow.ElbowState.OUT_ROBOT && Elbow.getHypotheticalState(angleInRads) != Elbow.ElbowState.OUT_ROBOT)));
        addCommands(new FromInGoOut(lengthInMeters, angleInRads)
                .unless(() -> !(Elbow.getInstance().state == Elbow.ElbowState.IN_BELLY && Elbow.getHypotheticalState(angleInRads) != Elbow.ElbowState.IN_BELLY)));
    }

    public GoToPosition(RobotMap.TelescopicArm.PresetPositions position) {
        this(position.distance, position.angleInRadians);
    }


}
