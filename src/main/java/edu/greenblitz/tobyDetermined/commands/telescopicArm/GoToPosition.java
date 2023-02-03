package edu.greenblitz.tobyDetermined.commands.telescopicArm;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.RotateToAngle;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtendToLength;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class GoToPosition extends ParallelCommandGroup {

    public GoToPosition (double lengthInMeters, double angleInRads){
        addCommands(new RotateToAngle(angleInRads),new ExtendToLength(lengthInMeters));
    }

    public GoToPosition (RobotMap.telescopicArm.presetPositions position){
        this(position.distance,position.angleInRadians);
    }




}
