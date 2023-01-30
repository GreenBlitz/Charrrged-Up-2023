package edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands;

import edu.greenblitz.tobyDetermined.commands.TelescopicArm.ExtenderCommand;
import edu.greenblitz.tobyDetermined.commands.TelescopicArm.TelescopicArmCommand;
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
            addCommands(new extendToLength(position.distance));
            return;
        }

        if(!isInTheSameSide(Elbow.getInstance().getAngle(), Units.degreesToRadians(position.angleInDegrees))){

        }


    }


    public boolean isInTheSameSide (double angle1, double angle2) {//in rads
        return (Elbow.getHypotheticalState(angle1) == Elbow.getHypotheticalState(angle2) && Elbow.getHypotheticalState(angle2) == Elbow.ElbowState.IN_ROBOT){

    }

}
