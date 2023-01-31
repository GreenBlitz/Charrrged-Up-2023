package edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands;

import edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands.Elbow.RotateToAngle;
import edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands.Extender.ExtendToLength;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Elbow;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Extender;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static edu.greenblitz.tobyDetermined.commands.TelescopicArm.Commands.armUtils.isInTheSameSide;

public class getToPresetPosition extends GBCommand {

    Extender.presetPositions position;

    public getToPresetPosition (Extender.presetPositions position){
        this.position = position;
        new GoToPosition(position.distance, position.angleInRadians).schedule();
    }



}
