package edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands.armUtils.isInTheSameSide;

public class GoToPosition extends SequentialCommandGroup {

    private double length;
    private double angle;

    public GoToPosition (double lengthInMeters, double angleInRads){
        this.length = lengthInMeters;
        this.angle = angleInRads;


        if(isInTheSameSide(Elbow.getInstance().getAngle(), angleInRads)){
            addCommands(new RotateToAngle(angleInRads));
            addCommands(new ExtendToLength(length));
            return;
        }else{
            addCommands(new ExtendToLength(0));
            addCommands(new RotateToAngle(angleInRads));
            addCommands(new ExtendToLength(length));
        }


    }




}
