package edu.greenblitz.tobyDetermined.commands.telescopicArm;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.RotateToAngle;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtendToLength;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class GoToPosition extends SequentialCommandGroup {

    public GoToPosition(double lengthInMeters, double angleInRads) {
        if(Extender.getHypotheticalState(lengthInMeters) == Extender.ExtenderState.ENTRANCE_LENGTH || Elbow.getInstance().isInTheSameState(angleInRads)){
            addCommands(new RotateToAngle(angleInRads).alongWith(new ExtendToLength(lengthInMeters)));
        }else{
            addCommands(new ExtendToLength(Math.min(RobotMap.telescopicArm.extender.MAX_ENTRANCE_LENGTH,lengthInMeters)));
            addCommands(new RotateToAngle(angleInRads));
            addCommands(new ExtendToLength(angleInRads));
        }
    }

    public GoToPosition(RobotMap.telescopicArm.presetPositions position) {
        this(position.distance, position.angleInRadians);
    }


}
