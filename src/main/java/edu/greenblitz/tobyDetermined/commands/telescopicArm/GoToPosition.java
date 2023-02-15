package edu.greenblitz.tobyDetermined.commands.telescopicArm;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.RotateToAngle;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtendToLength;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ElbowSim;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ExtenerSim;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class GoToPosition extends SequentialCommandGroup {

    public GoToPosition(double lengthInMeters, double angleInRads) {
        if(ExtenerSim.getHypotheticalState(lengthInMeters) == ExtenerSim.ExtenderState.IN_WALL_LENGTH || ElbowSim.getInstance().isInTheSameState(angleInRads)){
            addCommands(new RotateToAngle(angleInRads).alongWith(new ExtendToLength(lengthInMeters)));
        }
        //if the desired position needs to pass through the entrance zone and the extension to long the movement is split to multiple parts
        else{
            addCommands(new ExtendToLength(Math.min(RobotMap.telescopicArm.extender.MAX_ENTRANCE_LENGTH,lengthInMeters)));
            addCommands(new RotateToAngle(Math.min(RobotMap.telescopicArm.elbow.END_WALL_ZONE_ANGLE + 1,lengthInMeters)));
            addCommands(new RotateToAngle(angleInRads).alongWith(new ExtendToLength(lengthInMeters)));

            //addCommands(new RotateToAngle(angleInRads).alongWith(new ExtendToLength(lengthInMeters)));
        }
    }

    public GoToPosition(RobotMap.telescopicArm.presetPositions position) {
        this(position.distance, position.angleInRadians);
    }


}
