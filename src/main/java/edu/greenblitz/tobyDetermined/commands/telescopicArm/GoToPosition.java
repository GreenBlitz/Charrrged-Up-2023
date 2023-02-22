package edu.greenblitz.tobyDetermined.commands.telescopicArm;


import edu.greenblitz.tobyDetermined.RobotMap;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.elbow.RotateToAngle;
import edu.greenblitz.tobyDetermined.commands.telescopicArm.extender.ExtendToLength;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class GoToPosition extends SequentialCommandGroup {

    public GoToPosition(double lengthInMeters, double angleInRads) {
        //if the extender is inside the robot.
        if(Extender.getHypotheticalState(lengthInMeters) == Extender.ExtenderState.IN_WALL_LENGTH
                || Elbow.getInstance().isInTheSameState(angleInRads)){
            addCommands(new RotateToAngle(angleInRads).alongWith(new ExtendToLength(lengthInMeters)));
        }
        //if the desired position needs to pass through the entrance zone and the extension to long the movement is split to multiple parts
        else{
            //if the Elbow is outside the robot, and you want it in your belly(yummy yummy)
            if(Elbow.getInstance().state == Elbow.ElbowState.OUT_ROBOT &&
                    Elbow.getHypotheticalState(angleInRads) == Elbow.ElbowState.IN_BELLY){
                addCommands(
                        new ExtendToLength(
                                Math.min(RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH,lengthInMeters))
                                .alongWith(new RotateToAngle(RobotMap.TelescopicArm.Elbow.END_WALL_ZONE_ANGLE))
                );
                addCommands(new RotateToAngle(RobotMap.TelescopicArm.Elbow.STARTING_WALL_ZONE_ANGLE)
                        .andThen(
                                new RotateToAngle(angleInRads)
                                        .andThen(new ExtendToLength(lengthInMeters))
                        )
                );
                //if the Elbow is in your belly(yummy yummy) and you want it outside the robot
            }else if (Elbow.getInstance().state == Elbow.ElbowState.IN_BELLY
                    && Elbow.getHypotheticalState(angleInRads) == Elbow.ElbowState.OUT_ROBOT){
                addCommands(
                        new ExtendToLength(
                                Math.min(RobotMap.TelescopicArm.Extender.MAX_ENTRANCE_LENGTH,lengthInMeters))
                                .alongWith(new RotateToAngle(RobotMap.TelescopicArm.Elbow.STARTING_WALL_ZONE_ANGLE))
                );
                addCommands(new RotateToAngle(RobotMap.TelescopicArm.Elbow.END_WALL_ZONE_ANGLE)
                        .andThen(
                                new RotateToAngle(angleInRads)
                                        .andThen(new ExtendToLength(lengthInMeters))
                        )
                );
            }else{
                // STOPSHIP: 2/15/2023  
            }
        }
    }

    public GoToPosition(RobotMap.TelescopicArm.PresetPositions position) {
        this(position.distance, position.angleInRadians);
    }


}