package edu.greenblitz.tobyDetermined.commands.telescopicArm.claw;

import edu.greenblitz.tobyDetermined.subsystems.telescopicArm.ObjectSelector;
import edu.greenblitz.utils.GBCommand;

public class ObjectPositionByNode extends GBCommand {
    public ObjectPositionByNode(int object){
        if(object == 1) {
            ObjectSelector.selectCone();
            new GripBelly();
        }

        else if (object == 2) {
            ObjectSelector.selectCube();
            new GripBelly();
        }

        else if(object == 3) {
            new ReleaseObject();
            ObjectSelector.selectCube();
        }
    }

}
