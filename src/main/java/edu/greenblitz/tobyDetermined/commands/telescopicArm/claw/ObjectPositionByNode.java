package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw.ClawState;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.greenblitz.utils.GBCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ObjectPositionByNode extends GBCommand {
    public static Command getCommandFromState(ClawState object){
        switch (object){
            case CONE_MODE:
            case CUBE_MODE:
                return new GripBelly();
            case RELEASE:
                return new ReleaseObject();
        }
        return new InstantCommand();
    }

}
