package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.Claw.ClawState;
import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.greenblitz.utils.GBCommand;

public class ObjectPositionByNode extends GBCommand {
    public ObjectPositionByNode(ClawState object){
        switch (object){
            case CONE_MODE:
                ObjectSelector.selectCone();
                new GripBelly();
                break;
            case CUBE_MODE:
                ObjectSelector.selectCube();
                new GripBelly();
                break;
            case RELEASE:
                new ReleaseObject();
                ObjectSelector.selectCube();
                break;
        }
    }

}
