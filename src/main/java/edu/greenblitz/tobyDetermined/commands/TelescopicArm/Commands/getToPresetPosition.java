package edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class getToPresetPosition extends SequentialCommandGroup {

    Extender.presetPositions position;

    public getToPresetPosition (Extender.presetPositions position){
        this.position = position;

        if(isInTheSameSide(Elbow.getInstance().getAngle(), Units.degreesToRadians(position.angleInDegrees))){
            addCommands(new RotateToAngle(Units.degreesToRadians(position.angleInDegrees)));
            addCommands(new ExtendToLength(position.distance));
            return;
        }else{
            addCommands(new ExtendToLength(0));
            addCommands(new RotateToAngle(Units.degreesToRadians(position.angleInDegrees)));
            addCommands(new ExtendToLength(position.distance));
        }
    }


    public boolean isInTheSameSide (double angle1, double angle2) {//in rads
        return (Elbow.getHypotheticalState(angle1) == Elbow.getHypotheticalState(angle2));

    }

}
